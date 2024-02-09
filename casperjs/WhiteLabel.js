//casper.options.verbose = true;
//casper.options.logLevel = "debug";
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

function changeColor(color) {

	casper.test.begin('Change Color White Label Settings: ' + host, 0, function suite(test) {

		casper.then(function () {
			casper.start(host + '/account/whitelabel', function () {
				x = require('casper').selectXPath;

				this.waitForSelector(x("//button[contains(.,'" + color + "')]"), function () {
					this.thenClick(x("//button[contains(.,'" + color + "')]"), function () {

						casper.waitFor(function () {

							return casper.evaluate(function () {
								var modal = __utils__.getElementByXPath("//button[contains(.,' Save color scheme ')]").getAttribute("disabled");
								return modal === null;
							});

						}, function then() {
							this.thenClick(x("//button[contains(.,' Save color scheme ')]"), function () {
								this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
									casper.test.assertTextExists('The color scheme has been successfully changed', 'page saved propelry');
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

function changeCompanyDetails(name, logo, favicon) {
	casper.test.begin('Test WhiteLabel Company Details: ' + host, 0, function suite(test) {

		casper.start(host + '/account/whitelabel', function () {
			x = require('casper').selectXPath;

			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("whitelabel") > 0;
			}, function onReceived() {
				casper.test.pass('a secure resource has been loaded.');
			}, null, 15000);

			this.waitForSelector(x("//strong[contains(.,'Company details')]"), function () {
				this.thenClick(x("//strong[contains(.,'Company details')]"), function () {
					this.waitForSelector('#companyName', function () {
						casper.sendKeys('#companyName', name, {
							reset : true
						});
					});
					this.waitForSelector('#companyLogo', function () {
						casper.sendKeys('#companyLogo', logo, {
							reset : true
						});
					});
					this.waitForSelector('#companyFavicon', function () {
						casper.sendKeys('#companyFavicon', favicon, {
							reset : true
						});
					});
					casper.capture('check.png');

					this.waitFor(function () {
						return this.evaluate(function () {
							var e = __utils__.getElementByXPath("//button[contains(.,'Save company details')]").getAttribute("disabled");
							__utils__.echo("Val" + e);
							return e == null;
						});
					}, function then() {
						casper.thenClick(x("//button[contains(.,'Save company details')]"), function () {
							this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
								casper.test.assertTextExists('The company details have been successfully changed and the page will reload to get the latest changes.', 'page saved propelry');
								this.waitForSelector(x("//strong[contains(.,'Company details')]"), function () {
									this.thenClick(x("//strong[contains(.,'Company details')]"), function () {
										casper.thenClick(x("//button[contains(.,'Save company details')]"), function () {
											this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
												casper.test.assertTextExists('The company details have been successfully changed and the page will reload to get the latest changes.', 'page saved properly');
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

function changeHostName(name) {
	casper.test.begin('Test whiteLabel host name : ' + host, 0, function suite(test) {

		casper.start(host + '/account/whitelabel', function () {
			x = require('casper').selectXPath;
			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("whitelabel") > 0;
			}, function onReceived() {
				casper.test.pass('whitelabel resource has been loaded.');
			}, null, 15000);
			casper.scrollToBottom();
			this.waitForSelector(".card.mb-5:nth-child(3) .custom-control.custom-checkbox.m-0", function () {
				this.thenClick(".card.mb-5:nth-child(3) .custom-control.custom-checkbox.m-0", function () {

					this.waitFor(function () {
						return this.evaluate(function () {
							var name = __utils__.getElementByXPath("//label[contains(.,'  Hostname')]").getAttribute("title");
							return name == "Disable";
						});
					}, function () {
						this.waitForSelector('#hostnameMapping', function () {
							casper.sendKeys('#hostnameMapping', name, {
								reset : true
							});
							this.waitFor(function () {
								return this.evaluate(function () {
									var e = __utils__.getElementByXPath("//button[contains(.,' Save hostname ')]").getAttribute("disabled");
									return e == null;
								});

							}, function then() {
								casper.thenClick(x("//button[contains(.,' Save hostname ')]"), function () {
									this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
										casper.test.assertTextExists('The host mapping has been successfully changed.', 'page saved propelry');
										casper.thenOpen(host + '/account/whitelabel', function () {
											this.waitForSelector(".card.mb-5:nth-child(3) .custom-control.custom-checkbox.m-0", function () {
												this.thenClick(".card.mb-5:nth-child(3) .custom-control.custom-checkbox.m-0", function () {
													this.waitForSelector(x("//button[contains(.,' Save hostname ')]"), function () {
														casper.thenClick(x("//button[contains(.,' Save hostname ')]"), function () {
															this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
																casper.test.assertTextExists('The host mapping has been successfully changed.', 'page saved propelry');
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
			});

		});

		casper.run(function () {
			test.done();
		});

	});
}
function changeSMTP(server, port, security, user, pass, sender_email, sender_name) {
	casper.test.begin('Test WhiteLabel SMTP: ' + host, 0, function suite(test) {

		casper.start(host + '/account/whitelabel', function () {
			x = require('casper').selectXPath;
			casper.scrollToBottom();
			casper.waitForResource(function testResource(resource) {
				return resource.url.indexOf("whitelabel") > 0;
			}, function onReceived() {
				casper.test.pass('a secure resource has been loaded.');
			}, null, 15000);

			this.waitForSelector(x("//label[contains(.,'  SMTP')]"), function () {
				this.thenClick(x("//label[contains(.,'  SMTP')]"), function () {
					this.waitForSelector('#smtpServer', function () {
						casper.sendKeys('#smtpServer', server, {
							reset : true
						});
					});
					this.waitForSelector('#smtpPort', function () {
						casper.sendKeys('#smtpPort', port, {
							reset : true
						});
					});
					this.waitForSelector('#smtpSecurity', function () {
						this.evaluate(function (wsecurity) {
							return $(awr.whitelabel.smtpSecurity = wsecurity);
						}, security);
					});

					this.waitForSelector('#smtpUsername', function () {
						casper.sendKeys('#smtpUsername', user, {
							reset : true
						});
					});
					this.waitForSelector('#smtpPassword', function () {
						casper.sendKeys('#smtpPassword', pass, {
							reset : true
						});
					});
					this.waitForSelector('#smtpSenderEmail', function () {
						casper.sendKeys('#smtpSenderEmail', sender_email, {
							reset : true
						});
					});
					this.waitForSelector('#smtpSenderName', function () {
						casper.sendKeys('#smtpSenderName', sender_name, {
							reset : true
						});
					});

					this.waitFor(function () {
						return this.evaluate(function () {
							var e = __utils__.getElementByXPath("//button[contains(.,' Save SMTP settings ')]").getAttribute("disabled");
							return e == null;
						});
					}, function then() {

						casper.thenClick(x("//button[contains(.,' Save SMTP settings ')]"), function () {

							this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
								casper.test.assertTextExists('The SMTP details have been changed.', 'page saved propelry');
							});

						});

						casper.thenOpen(host + '/account/whitelabel', function () {

							this.waitForSelector(x("//button[contains(.,' Test SMTP settings ')]"), function () {
								this.thenClick(x("//button[contains(.,' Test SMTP settings ')]"), function () {
									this.waitUntilVisible('.awrAlert__content.awrAlert__content--info.align-self-stretch>span', function () {
										casper.test.assertTextExists('Your SMTP settings have been successfully tested.', 'page saved properly');
									}, null, 10000);
								});
							});

						});
						this.thenOpen(host + '/account/whitelabel', function () {
							casper.scrollToBottom();
							this.waitForSelector(x("//label[contains(.,'  SMTP')]"), function () {
								this.thenClick(x("//label[contains(.,'  SMTP')]"), function () {
									this.waitFor(function () {
										return this.evaluate(function () {
											var e = __utils__.getElementByXPath("//button[contains(.,' Save SMTP settings ')]").getAttribute("disabled");
											return e == null;
										});
									}, function then() {
										this.thenClick(x("//button[contains(.,' Save SMTP settings ')]"), function () {
											casper.wait(1000);
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

changeColor("Red");
changeColor('Blue');

changeCompanyDetails("Caphyon", "https://cdn.colorlib.com/wp/wp-content/uploads/sites/2/2014/02/Olympic-logo.png", "https://cdn.colorlib.com/wp/wp-content/uploads/sites/2/2014/02/Olympic-logo.png");

changeHostName("mimi.advancedwebranking.com");

changeSMTP("smtp.googlemail.com", "465", "ssl", "alex410139@gmail.com", "romtelecom", "qadevm@gmail.com", "alex");