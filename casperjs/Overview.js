casper.options.verbose = true;
casper.options.logLevel = "debug";
var global = require('../config/casper_utils');
var host = global.settings.URL.dev;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;
var mouse = require("mouse").create(casper);
var projectID = global.settings.projectID2
global.logout(casper, host);
global.login(casper, userEmail, userPass, host);
casper.options.viewportSize = {
	width : 1355,
	height : 5000
};

function getSel(sel) {

	var val = document.getElementById(sel).value;
	return val;
}
function changePrefences(frequency, depth, country, report, date, flag, wname) {

	casper.test.begin('Test Overview Settings: ' + host, 0, function suite(test) {

		casper.then(function () {
			casper.start(host + '/settings/overview?project_id='+projectID, function () {
				x = require('casper').selectXPath;
				casper.waitForResource(function testResource(resource) {
					return resource.url.indexOf("overview") > 0;
				}, function onReceived() {
					casper.test.pass('overview link has been loaded');

					casper.then(function () {
						console.log('settings values' + frequency);
						this.evaluate(function (wfreq, wdepth, wcountry, wreport, wdate) {
							$(awr.overview_preferences.selectedFrequency = wfreq);
							$(awr.overview_preferences.websiteDepth = wdepth);
							$(awr.overview_preferences.websiteCountry = wcountry);
							$(awr.overview_preferences.websiteQuickReports = wreport);
							$(awr.overview_preferences.websiteDateFormat = wdate);
						}, frequency, depth, country, report, date);
					});
					casper.then(function () {
						this.evaluate(function () {
							document.querySelectorAll('.btn.btn-primary')[3].click();
						});
					});

					casper.then(function () {

						this.waitForSelector('.awrAlert__content.awrAlert__content--info.align-self-stretch', function () {
							console.log('flag' + flag);
							casper.test.assertTextExists('The website preferences have been successfully changed', 'page saved propelry');
							if (flag) {
								casper.test.assert(
									this.evaluate(function (wfreq) {
										return document.getElementById('websiteFrequency').value === wfreq + '';
									}, frequency), 'WebSiteFrequency setting value');

								casper.test.assert(
									this.evaluate(function (wdepth) {
										return document.getElementById('websiteDepth').value === wdepth + '';
									}, depth), 'WebSiteDepth setting value');

								casper.test.assert(
									this.evaluate(function (wcountry) {
										return document.getElementById('websiteCountry').value === wcountry + '';
									}, country), 'WebSiteCountry setting value');

								casper.test.assert(

									this.evaluate(function (wreport) {
										return document.getElementById('websiteQuickReports').value === wreport + '';
									}, report), 'websiteQuickReports setting value');

								casper.test.assert(
									this.evaluate(function (wdate) {
										return document.getElementById('websiteDateFormat').value === wdate;
									}, date), 'websiteDateFormat setting value');
							}
						});
					});
				})

			});

		});

		casper.run(function () {
			test.done();
		});

	});

}

function changeName(name) {

	casper.test.begin('Change Website name : ' + host, 0, function suite(test) {

		casper.then(function () {
			casper.start(host + '/settings/overview?project_id='+projectID, function () {
				casper.waitForResource(function testResource(resource) {
					return resource.url.indexOf("overview") > 0;
				}, function onReceived() {
					casper.test.pass('rest link has been loaded');
					casper.then(function () {

						casper.waitForSelector('#websiteNameID', function () {
							casper.thenClick('#websiteNameID', function () {
								console.log("New name is " + name);
								casper.sendKeys('#websiteNameID', name, {
									reset : true
								});
								casper.waitForSelector(x("//button[@id='save-basic-settings']"), function () {
									casper.thenClick(x("//button[@id='save-basic-settings']"), function () {});

								});
							});

						});
					});

					casper.then(function () {

						this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch', function () {
							casper.test.assertTextExists('The website settings have been successfully changed', 'page saved propelry');
							casper.test.assert(
								this.evaluate(function (wname) {
									return document.getElementById('websiteNameID').value === wname;
								}, name), 'Name of website');
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

changePrefences(3, 10, 2, 0, "MMM dd, yyyy", true);

changePrefences(3, 5, 3, 1, "MMM dd, yyyy", false);

changeName("Teste");
changeName("booking.com");
