casper.options.verbose = true;
casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev; ;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
var projectID = global.settings.projectID2
	x = require('casper').selectXPath;

global.logout(casper, host);
global.login(casper, userEmail, userPass, host);

function SelectColumns() {

	casper.test.begin('Add or remove columns to keyword ranking' + host, 0, function suite(test) {

		casper.then(function () {
			casper.start(host + "/ranking/keywords?project_id=" + projectID, function () {

				casper.waitForSelector(x("(//button[@data-toggle='dropdown'])[8]"), function () {
					casper.thenClick(x("(//button[@data-toggle='dropdown'])[8]"), function () {
						casper.waitUntilVisible('.dropdown.btn-group.show', function () {
							casper.waitUntilVisible(x("//button[contains(.,'Best Position')]"), function () {
								casper.thenClick(x("//button[contains(.,'Best Position')]"), function () {
									casper.waitUntilVisible(x("//button[contains(.,'Competition')]"), function () {
										casper.thenClick(x("//button[contains(.,'Competition')]"), function () {
											casper.waitUntilVisible(x("//button[contains(.,'Searches')]"), function () {
												casper.thenClick(x("//button[contains(.,'Searches')]"), function () {
													casper.waitUntilVisible(x("//button[contains(.,'CPC')]"), function () {
														casper.thenClick(x("//button[contains(.,'CPC')]"), function () {});
													});

												});
											});

										});
									});

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
}

function CheckIfColumnsExist() {

	casper.test.begin('Check if columns exists in keyword ranking' + host, 0, function suite(test) {

		casper.start(host + "/ranking/keywords?project_id=" + projectID, function () {

			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("apiEndpoint") > 0;
			}, function onReceived() {
				casper.test.pass('apiEndpoint link has been loaded');

				casper.waitForSelector(x("//*[contains(text(), \'Best Position\')]"),
					function success() {
					test.assertExists(x("//*[contains(text(), \'Best Position\')]"));
				},
					function fail() {
					test.assertExists(x("//*[contains(text(), \'Best Position\')]"));
				});

				casper.waitForSelector(x("//*[contains(text(), \'Competition\')]"),
					function success() {
					test.assertExists(x("//*[contains(text(), \'Competition\')]"));
				},
					function fail() {
					test.assertExists(x("//*[contains(text(), \'Competition\')]"));
				});

				casper.waitForSelector(x("//*[contains(text(), \'Searches\')]"),
					function success() {
					test.assertExists(x("//*[contains(text(), \'Searches\')]"));
				},
					function fail() {
					test.assertExists(x("//*[contains(text(), \'Searches\')]"));
				});

				casper.waitForSelector(x("//*[contains(text(), \'CPC\')]"),
					function success() {
					test.assertExists(x("//*[contains(text(), \'CPC\')]"));
				},
					function fail() {
					test.assertExists(x("//*[contains(text(), \'CPC\')]"));
				});

			});

		});

		casper.run(function () {
			test.done();
		});

	});
}

function CheckIfColumnsDoesntExist() {

	casper.test.begin('Check if columns exists in keyword ranking' + host, 0, function suite(test) {

		casper.start(host + "/ranking/keywords?project_id=" + projectID, function () {

			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("apiEndpoint") > 0;
			}, function onReceived() {
				casper.test.pass('apiEndpoint link has been loaded');

				test.assertDoesntExist(x("//*[contains(text(), \'Best Position\')]"));
				test.assertDoesntExist(x("//*[contains(text(), \'Competition\')]"));
				test.assertDoesntExist(x("//*[contains(text(), \'Searches\')]"));
				test.assertDoesntExist(x("//*[contains(text(), \'CPC\')]"));

			});

		});

		casper.run(function () {
			test.done();
		});

	});
}

SelectColumns();
CheckIfColumnsExist();
SelectColumns();
CheckIfColumnsDoesntExist();