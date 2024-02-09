//casper.options.verbose = true;
//casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;
var mouse = require("mouse").create(casper);
global.logout(casper, host);
global.login(casper, userEmail, userPass, host);
casper.options.viewportSize = {
	width : 1355,
	height : 5000
};

function changeWebsiteFrequency(frequency) {

	casper.test.begin('Test Website Frequency from Websites page: ' + host, 0, function suite(test) {

		casper.start(host, function () {
			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("apiEndpoint") > 0;
			}, function onReceived() {
				casper.test.pass('apiEndpoint link has been loaded');

				casper.then(function () {
					casper.waitForSelector('.dropdown-toggle.btn.btn-secondary.btn-sm', function () {

						this.evaluate(function () {
							document.querySelector('.dropdown-toggle.btn.btn-secondary.btn-sm').click()
						});

					});

				});

				casper.then(function () {
					casper.waitUntilVisible('.dropdown.btn-group.show', function () {
						if (frequency === "On demand") {
							casper.thenClick(x("(//a[contains(.,'" + frequency + "')])[2]"), function () {});
						} else {
							casper.thenClick(x("(//a[contains(.,'" + frequency + "')])[1]"), function () {});
						}
					});
				});

				casper.then(function () {
					casper.waitForResource(function testResource(resource) {
						return resource.url.indexOf("apiEndpoint") > 0;
					}, function onReceived() {

						casper.waitFor(function () {
							return casper.evaluate(function (wfrequency) {
								var e = document.querySelector('.dropdown-toggle.btn.btn-secondary.btn-sm').innerHTML.trim();
								return e === wfrequency;
							}, frequency);
						}, function then() {

							casper.test.assert(
								this.evaluate(function (wfrequency) {
									return document.querySelector('.dropdown-toggle.btn.btn-secondary.btn-sm').innerHTML.trim() === wfrequency;
								}, frequency), 'Frequency value');
						});

					});
				});

			})

		});

		casper.run(function () {
			test.done();
		});

	});

}

var s, freq = ["Daily", "Weekly","Biweekly","Monthly","On demand"];
for (s of freq) {
	changeWebsiteFrequency(s);
}
