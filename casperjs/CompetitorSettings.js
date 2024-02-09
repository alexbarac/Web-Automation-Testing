var global = require('../config/casper_utils');
var host = global.settings.URL.dev; ;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
var projectID = global.settings.projectID1;
	x = require('casper').selectXPath;

global.logout(casper, host);
global.login(casper, userEmail, userPass, host);

casper.test.begin('Add and Delete competitor' + host, 0, function suite(test) {

	x = require('casper').selectXPath;

	casper.then(function () {
		casper.start(host + "/settings/competitors?project_id=" + projectID, function () {
			casper.waitForSelector(x("//button[contains(.,'Add competitors')]"), function () {
				casper.thenClick(x("//button[contains(.,'Add competitors')]"), function () {
					casper.waitUntilVisible('.modal.fade.show', function () {
						casper.then(function () {
							casper.sendKeys('#competitorsTextarea', "www.mapamond.ro");
						});
						this.waitFor(function () {
							casper.capture("screen/screen1.png");
							return this.evaluate(function () {
								var name = __utils__.findOne('#add-competitors-modal > div > div > div.modal-footer.awrModal__footer > button').getAttribute("disabled");

								__utils__.echo("waiting for button Save  " + name);

								return name == null;
							});
						}, function () {
							casper.click('#add-competitors-modal > div > div > div.modal-footer.awrModal__footer > button');
						});
					});
				});

			});

			casper.then(function () {

				casper.start(host + "/settings/competitors", function () {
					casper.waitUntilVisible('.table.table-responsive.table-striped>tbody', function () {
						casper.waitUntilVisible('.d-inline-block.mr-1', function () {
							test.assertSelectorHasText('.d-inline-block.mr-1', 'www.mapamond.ro');

						});
					});
				});

			});

			casper.then(function () {

				casper.start(host + "/settings/competitors", function () {

					var selectors = [
						'html>body',
						'HTML > BODY > SCRIPT',
						'#ranking_table_overlay_',
						'div'
					];

					casper.waitForSelector(selectors.join(', '), function () {

						casper.waitUntilVisible("thead:nth-child(2) input", function () {
							casper.thenClick("thead:nth-child(2) input", function () {
								casper.waitUntilVisible('DIV.dropdown.btn-group', function () {
									casper.thenClick(x("//button[contains(.,'Manage')]"), function () {
										test.assertExists(x("//button[contains(.,'Manage')]"));
										casper.waitUntilVisible(x("//a[normalize-space(text())='Delete selected']"), function () {
											casper.thenClick(x("//a[normalize-space(text())='Delete selected']"), function () {
												this.echo("Delete Competitor");
												casper.setFilter('page.confirm', function (message) {
													self.received = message;
													this.echo("message to confirm : " + message);
													return true;
												});

											})
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

			casper.then(function () {

				casper.reload(function () {
					casper.waitWhileSelector('.table.table-responsive.table-striped', function () {
						test.assertTextDoesntExist('www.mapamond.ro', 'page body does not contain "www.mapamond.ro"');
					});
				});

			});

		});

	});

	casper.run(function () {
		test.done();
	});

});