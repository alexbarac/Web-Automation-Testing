casper.options.verbose = true;
casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/49.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;

global.logout(casper, host);
global.login(casper, userEmail, userPass, host);
casper.options.viewportSize = {
	width : 1355,
	height : 1070
};

function changeFilter(filter) {

	casper.test.begin('Change Website Filters: ' + host, 0, function suite(test) {

		casper.start(host, function () {

			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("apiEndpoint") > 0;
			}, function onReceived() {

				casper.then(function () {

					casper.test.pass('apiEndpoint link has been loaded');
					this.waitFor(function () {
						return casper.evaluate(function () {
							var e = document.querySelector('#updateButton').getAttribute('disabled');
							return e == null;
						});
					}, function then() {
						this.thenClick('#filter-actions', function () {
							this.waitUntilVisible('.dropdown.btn-group.show', function () {
								this.waitForSelector(x("//a[contains(.,'" + filter + "')]"), function () {
									this.thenClick(x("//a[contains(.,'" + filter + "')]"), function () {});
								});

							});

						});
					});
				});

				casper.then(function () {
					if (filter === "Scheduled") {
						casper.waitForSelectorTextChange('.h4', function () {
							test.assertEval(function () {
								var elems = document.querySelectorAll('.dropdown-toggle.btn.btn-secondary.btn-sm');
								for (i = 0; i < elems.length; i++) {
									var val = document.querySelectorAll('.dropdown-toggle.btn.btn-secondary.btn-sm').item(i).innerHTML.trim();
									if (val != "Daily" && val != "Weekly" && val != "Biweekly" && val != "Monthly") {
										__utils__.echo("Found project with Frequency/Update value equal to: " + val + " even when scheduled filter is on.");
										return false;
									}
								}
								return true;
							}, "Checking scheduled filter");
						});
					}
					if (filter === "On demand") {
						casper.waitForSelectorTextChange('.h4', function () {
							test.assertEval(function () {
								var elems = document.querySelectorAll('.dropdown-toggle.btn.btn-secondary.btn-sm');
								for (i = 0; i < elems.length; i++) {
									var val = document.querySelectorAll('.dropdown-toggle.btn.btn-secondary.btn-sm').item(i).innerHTML.trim();
									if (val != "On demand") {
										__utils__.echo("Found project with Frequency/Update value equal to: " + val + " even when On demand filter is on.");
										return false;
									}
								}
								return true;
							}, "Checking On demand filter");
						});
					}
				});

			});

		});

		casper.run(function () {
			test.done();
		});

	});

}
/*
var s, filter = ["All websites","Scheduled", "On demand"];
for (s of filter) {
	changeFilter(s);
} */

var a = ["All websites","Scheduled", "On demand"];
a.forEach(function(entry) {
    changeFilter(entry);
});