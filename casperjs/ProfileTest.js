var global = require('../config/casper_utils');
var host = global.settings.URL.dev;
casper.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
var userEmail = global.settings.user.devs;
var userPass = global.settings.password.dev;
x = require('casper').selectXPath;
global.logout(casper, host);
global.login(casper, userEmail, userPass, host);

function changePreference(numberformat, currencyformat, timezone, daylight, flag) {

	casper.test.begin('Test account profile preferences settings: ' + host, 0, function suite(test) {

		casper.viewport(1280, 1024);
		casper.then(function () {
			casper.start(host + '/account/profile', function () {
				x = require('casper').selectXPath;
				casper.waitForResource(function testResource(resource) {
					return resource.url.indexOf("websites-dropdown") > 0;
				}, function onReceived() {
					casper.test.pass('websites-dropdown has been loaded');

					casper.then(function () {
						this.evaluate(function (wnumber, wcurrency, wtimezone, wdaylight) {
							return ($('#number_format_locale').val(wnumber).change() && $('#currency_format_locale').val(wcurrency).change() &&
								$('#timezone').val(wtimezone).change() &&
								$("#daylight_savings").prop('checked', wdaylight));

						}, numberformat, currencyformat, timezone, daylight);
					});
					casper.then(function () {
						casper.wait(500);
						this.evaluate(function () {
							document.querySelectorAll('.btn.btn-primary')[3].click();
						});

					});

					casper.then(function () {

						casper.waitForResource(function testResource(resource) {
							return resource.url.indexOf("profile") > 0;
						}, function onReceived() {
							casper.test.pass('page has been loaded');
							casper.test.assertTextExists('The preferences have been successfully updated', 'page saved propelry');
							if (flag) {
								casper.test.assert(
									this.evaluate(function (wnumber) {
										return document.getElementById('number_format_locale').value === wnumber;
									}, numberformat), 'Number format setting value');

								casper.test.assert(
									this.evaluate(function (wcurrency) {
										return document.getElementById('currency_format_locale').value === wcurrency;
									}, currencyformat), 'Currency format setting value');

								casper.test.assert(
									this.evaluate(function (wtimezone) {
										return document.getElementById('timezone').value === wtimezone;
									}, timezone), 'Time zone format setting value');

								casper.test.assert(
									this.evaluate(function (wdaylight) {
										return $('#daylight_savings').is(':checked') === wdaylight;
									}, daylight), 'Daylight savings setting value');

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

changePreference("el_CY", "el_CY", "+1", true, true);

changePreference("en_US", "en_US", "+2", false, false);