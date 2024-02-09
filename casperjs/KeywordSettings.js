//casper.options.verbose = true;
//casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev; ;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;

global.logout(casper, host);
global.login(casper, userEmail, userPass, host);

function waitForMultipleResources(casp, sel) {
	casp.waitForResource(function testResource(resource) {
		return resource.url.indexOf(sel) > 0;
	}, function onReceived() {
		casp.test.pass('a secure resource has been loaded.');
	}, null, 15000);
}

casper.test.begin('Add and delete keyword ' + host, 4, function suite(test) {

	x = require('casper').selectXPath;

	casper.then(function () {
		casper.start(host + "/settings/keywords?project_id=11", function () {
			casper.wait(1500);
			casper.waitForSelector('.nav-link.active', function () {

				casper.thenClick(x("(//button[contains(.,'Add keywords')])[1]"), function () {
					casper.waitUntilVisible('.modal.fade.show', function () {
						casper.then(function () {
							casper.sendKeys('#keywordsTextarea', "teste", {
								reset : true
							});
							test.assertExists(x("//label[contains(.,'Keywords (one per line)')]"));
							test.assertExists(x("//label[contains(.,'Assign keywords to the following group:')]"));

						});
						casper.then(function () {

							casper.waitFor(function () {
								return casper.evaluate(function () {
									var e = __utils__.getElementByXPath("//button[@id='addKeywordsBtn']").getAttribute("disabled");
									return e == null;
								});
							}, function then() {
								casper.thenClick('#addKeywordsBtn', function () {
									casper.waitUntilVisible('.d-inline-block.mr-1', function () {
										test.assertSelectorHasText('.d-inline-block.mr-1', 'teste');

									});
								});
							});
						});

					});
				});

				casper.then(function () {

					casper.start(host + "/settings/keywords", function () {
						casper.wait(1500);
						casper.waitForSelector(x("(//input[@type='checkbox'])[3]"), function () {
							casper.thenClick(x("(//input[@type='checkbox'])[3]"), function () {

								casper.waitUntilVisible(x("(//button[contains(.,'Manage')])[2]"), function () {
									casper.capture('keypage.png');
									casper.thenClick(x("(//button[contains(.,'Manage')])[2]"), function () {
										casper.waitUntilVisible('.dropdown.btn-group.show', function () {
											casper.thenClick(x("//a[normalize-space(text())='Delete selected']"), function () {
												this.echo("Delete Keyword");
												casper.setFilter('page.confirm', function (message) {
													self.received = message;
													this.echo("message to confirm : " + message);
													return true;
												});

											});
											casper.then(function () {
												casper.start(host + "/settings/keywords", function () {
													test.assertTextDoesntExist('teste', 'page body does not contain "teste"');
												});
											});
											casper.then(function () {
												global.logout(casper, host);
											});

										});

									})

								});

							})

						});
					});

				});

			});

		});

	});

	casper.run(function () {
		test.done();
	});

});