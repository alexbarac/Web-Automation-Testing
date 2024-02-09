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

casper.test.begin('Add and Delete search engine with country France and SEO Google' + host, 2, function suite(test) {

	x = require('casper').selectXPath;

	casper.then(function () {
		casper.start(host + "/settings/search-engines?project_id=5", function () {
			casper.waitForSelector(x("//button[contains(.,'Add search engines')]"), function () {
				casper.thenClick(x("//button[contains(.,'Add search engines')]"), function () {
					casper.waitUntilVisible('.modal.fade.show', function () {
						//casper.wait(1200);
						casper.waitForResource(function testResource(resource) {
							return resource.url.indexOf("AuthenticationService") > 0; 
						}, function onReceived() {
						});
						casper.waitForSelector('#country_dropdown', function () {
							casper.thenClick('#country_dropdown', function () {
								casper.capture('search.png');
								
								casper.waitUntilVisible('.dropdown.show', function () {
									casper.thenClick(x("//a[contains(.,' Germany ')]"), function () {
										casper.waitForSelector('#ses_dropdown', function () {
											casper.thenClick('#ses_dropdown', function () {
												//casper.wait(1000);
												casper.waitForResource(function testResource(resource) {
													return resource.url.indexOf("apiEndpoint") > 0;
												}, function onReceived() {
												});
												casper.waitUntilVisible('.dropdown.show', function () {
													casper.thenClick(x("//a[contains(.,' Bing ')]"), function () {
														casper.waitForSelector('.form-check-input', function () {
															casper.thenClick('.form-check-input', function () {

																casper.waitFor(function () {

																	return casper.evaluate(function () {
																		var modal = __utils__.getElementByXPath("//*[@id='ses_add_button']").getAttribute("disabled");
																		return modal === null;
																	});

																}, function then() {
																	//this.wait(3000);
																	this.evaluate(function () {
																	document.querySelector('#ses_add_button').click();
																	});

																});
															});
														});

													});
													casper.then(function () {  
														casper.waitForSelector('.d-inline-block.mr-1', function () {
															test.assertSelectorHasText('.d-inline-block.mr-1', 'Bing');  
														});
													});
													casper.then(function () {
														casper.waitForSelector('.d-inline-block.mr-1', function () {
															casper.thenClick(x("(//input[contains(@type,'checkbox')])[1]"), function () {
																casper.waitUntilVisible(x("(//button[contains(.,'Delete selected')])[1]"), function () {
																	casper.thenClick(x("(//button[contains(.,'Delete selected')])[1]"), function () {
																		this.echo("Delete SEO");

																		casper.setFilter('page.confirm', function (message) {
																			self.received = message;
																			this.echo("message to confirm : " + message);
																			return true;
																		});

																	});

																});
															});
														});

													});

													casper.then(function () {
														casper.thenOpen(host + "/settings/search-engines?project_id=5", function () {
															test.assertSelectorDoesntHaveText('.d-inline-block.mr-1', 'Bing');
														});

													});

													casper.then(function () {
														global.logout(casper, host);
													});

												});

											});
										});

									});
								}, null, 10000);

							});
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