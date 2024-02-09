casper.options.verbose = true;
casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;

global.logout(casper, host);
global.login(casper, userEmail, userPass, host);
casper.options.viewportSize = {
	width : 1355,
	height : 1070
};

function AddWebsite() {
	casper.test.begin('Add Web Site on platform: ' + host, 0, function suite(test) {

		casper.then(function () {
			casper.start(host, function () {
				casper.waitForSelector(x("//a[contains(.,' Add new website ')]"), function () {
					casper.thenClick(x("//a[contains(.,' Add new website ')]"), function () {

						casper.waitForSelector("form#new-website input[name='url']",
							function success() {
							test.assertExists("form#new-website input[name='url']");
							this.click("form#new-website input[name='url']");
						},
							function fail() {
							test.assertExists("form#new-website input[name='url']");
						});
						casper.waitForSelector("input[name='url']",
							function success() {
							this.sendKeys("input[name='url']", "www.baby.com");
						},
							function fail() {
							test.assertExists("input[name='url']");
						});
						casper.waitForSelector("#websitekeywords",
							function success() {
							test.assertExists("#websitekeywords");
							this.click("#websitekeywords");
						},
							function fail() {
							test.assertExists("#websitekeywords");
						});
						casper.waitForSelector("textarea#websitekeywords",
							function success() {
							this.sendKeys("textarea#websitekeywords", "baby");
						},
							function fail() {
							test.assertExists("textarea#websitekeywords");
						});
						casper.waitForSelector("#websitecompetitors",
							function success() {
							test.assertExists("#websitecompetitors");
							this.click("#websitecompetitors");
						},
							function fail() {
							test.assertExists("#websitecompetitors");
						});
						casper.waitForSelector("textarea#websitecompetitors",
							function success() {
							this.sendKeys("textarea#websitecompetitors", "baby.unica.ro");
						},
							function fail() {
							test.assertExists("textarea#websitecompetitors");
						});
						casper.waitForSelector("form#new-website button#new-website-submit",
							function success() {
							test.assertExists("form#new-website button#new-website-submit");
							this.click("form#new-website button#new-website-submit");
						},
							function fail() {
							test.assertExists("form#new-website button#new-website-submit");
						});

						casper.waitForSelector(x("//*[contains(text(), \'www.baby.com\')]"),
							function success() {
							test.assertExists(x("//*[contains(text(), \'www.baby.com\')]"));
						},
							function fail() {
							test.assertExists(x("//*[contains(text(), \'www.baby.com\')]"));
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

function DeleteWebsite(name) {
	casper.test.begin('Delete Web Site on platform: ' + host, 0, function suite(test) {
		casper.start(host, function () {
			casper.then(function () {
				casper.waitForSelector(x("//strong[contains(.,'baby.com')]"), function () {
					casper.test.pass('placeholdit link has been loaded');
					var found = this.evaluate(function (name) {
							var elemTitles = __utils__.getElementsByXPath("//tr/td[2]/div/div/a/strong");
							var checks = __utils__.getElementsByXPath("//tr/td[1]/label");
							for (i = 0; i < elemTitles.length; i++) {
								__utils__.echo("NAME" + elemTitles[i].innerHTML);
								__utils__.echo("index" + elemTitles[i].innerHTML.indexOf(name));
								if (elemTitles[i].innerHTML.indexOf(name) > -1) {
									__utils__.echo(i + " selected");
									__utils__.echo(elemTitles[i].innerHTML.trim() + " selected");
									__utils__.echo(elemTitles[i].innerHTML.indexOf(name) + " index");
									checks[i].click();
									return true;
								}
							}

							return false;
						}, name);
					if (!found) {
						casper.echo("Did not find something to delete");
					} else {
						casper.waitForSelector('.dropdown-toggle.btn.btn-primary', function () {
							casper.thenClick('.dropdown-toggle.btn.btn-primary', function () {
								casper.waitForSelector(x("//a[contains(.,'Delete websites')]"), function () {
									casper.thenClick(x("//a[contains(.,'Delete websites')]"), function () {});
								});
							});
						});
					}

				});
			});

			casper.then(function () {
				casper.waitWhileSelector(x("//strong[contains(.,'baby.com')]"), function () {
					casper.test.pass('placeholdit link has been loaded');
					test.assertDoesntExist(x("//strong[contains(.,'baby.com')]"));
				});

			});

		});

		casper.run(function () {
			test.done();
		});

	});

}
AddWebsite();
DeleteWebsite('www.baby.com');