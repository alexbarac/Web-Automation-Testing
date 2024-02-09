/// <reference types="cypress" />

import { Users } from '../support/constants';
import {NoRetriesConfig} from "../support/run-options";

describe('General settings test suite', () => {
  const settingsProfile = '/account/profile';
  beforeEach(() => {
    cy.login(Users.max);
  });

  it('Test scheduled updates preferences', () => {
    const frequency = {
      weekly: 'Monday',
      biweekly: 'Day 1 and 15 of the month',
      monthly: 'Day 1 of the month',
    };

    cy.visit(`${settingsProfile}`);
    cy.getBySelector('weekly-updates')
      .should('be.visible')
      .select(frequency.weekly);
    cy.getBySelector('biweekly-updates')
      .should('be.visible')
      .select(frequency.biweekly);
    cy.getBySelector('monthly-updates')
      .should('be.visible')
      .select(frequency.monthly);
    cy.getBySelector('save-preferences')
      .click();
    cy.checkSelectorText('alert-content', 'The preferences have been successfully updated');
    cy.reload().then(() => {
      cy.getBySelector('weekly-updates')
        .find('option:selected')
        .should('have.text', frequency.weekly);
      cy.getBySelector('biweekly-updates')
        .find('option:selected')
        .should('have.text', frequency.biweekly);
      cy.getBySelector('monthly-updates')
        .find('option:selected')
        .should('have.text', frequency.monthly);
    });
  });

  it('Test reporting preferences', () => {
    const clickShare = 'SERP Features Click Share';
    const numberFormat = '9.988,65';
    const currencyFormat = '$9.988,65';
    const timeZone = '[UTC - 6] Central Standard Time';

    cy.visit(`${settingsProfile}`);

    cy.getBySelector('click-share-curve')
      .should('be.visible')
      .select(clickShare);
    cy.getBySelector('number-format')
      .should('be.visible')
      .select(numberFormat);
    cy.getBySelector('currency-format')
      .should('be.visible')
      .select(currencyFormat);
    cy.getBySelector('time-zone')
      .should('be.visible')
      .select(timeZone);
    cy.getBySelector('save-button-reporting')
      .click();
    cy.getBySelector('daylight-savings-checkbox')
      .click();
    cy.checkSelectorText('alert-content', 'The preferences have been successfully updated');
    cy.reload().then(() => {
      cy.getBySelector('click-share-curve')
        .find('option:selected')
        .should(($el) => {
          expect($el.text().replace(/\s/g, ''))
            .to.be.eq('SERPFeaturesClickShare');
        });
      cy.getBySelector('number-format')
        .find('option:selected')
        .should('have.text', numberFormat);
      cy.getBySelector('currency-format')
        .find('option:selected')
        .should('have.text', currencyFormat);
      cy.getBySelector('time-zone')
        .find('option:selected')
        .should(($el) => {
          expect($el.text().replace(/\s/g, ''))
            .to.be.eq('[UTC-6]CentralStandardTime');
        });
    });
  });

  it('Test email updates', NoRetriesConfig,() => {
    cy.visit(`${settingsProfile}`);
    const frequency = ['Daily', 'Weekly', 'Biweekly', 'Monthly'];
    cy.get(Object.entries(frequency)).each(([index, value]) => {
      cy.getBySelector('email-frequency')
        .should('be.visible')
        .select(value);
      switch (value) {
        case 'Daily':
          cy.getBySelector('email-changes-daily')
            .should('be.visible')
            .select('All projects');
          cy.getBySelector('save-button-email')
            .click();
          cy.reload().then(() => {
            cy.getBySelector('email-changes-daily')
              .find('option:selected')
              .should('have.text', 'All projects');
            cy.getBySelector('email-list')
              .invoke('attr', 'value')
              .should('equal', 'max@gmail.com');
          });
          break;
        case 'Weekly':
          cy.getBySelector('email-changes-weekly')
            .should('be.visible')
            .select('Tuesday');
          cy.getBySelector('save-button-email')
            .click();
          cy.reload().then(() => {
            cy.getBySelector('email-changes-weekly')
              .find('option:selected')
              .should('have.text', 'Tuesday');
          });
          break;
        case 'Biweekly':
          cy.getBySelector('email-changes-biweekly')
            .should('be.visible')
            .select('Day 1 and 15 of the month');
          cy.getBySelector('save-button-email')
            .click();
          cy.reload().then(() => {
            cy.getBySelector('email-changes-biweekly')
              .find('option:selected')
              .should('have.text', 'Day 1 and 15 of the month');
          });
          break;
        case 'Monthly':
          cy.getBySelector('email-changes-monthly')
            .should('be.visible')
            .select('Day 1 of the month');
          cy.getBySelector('save-button-email')
            .click();
          cy.reload().then(() => {
            cy.getBySelector('email-changes-monthly')
              .find('option:selected')
              .should('have.text', 'Day 1 of the month');
          });
          break;
        default:
          break;
      }
    });
  });

  it('Test cancel account', () => {
    cy.visit(`${settingsProfile}`);
    cy.getBySelector('cancel-account')
      .should('not.be.disabled')
      .click();
    cy.checkSelectorText('cancel-alert', 'Are you sure you want to cancel');
    cy.getBySelector('reason_8')
      .should('not.be.disabled')
      .click();
    cy.getBySelector('cancel_account_reason_8')
      .type('ranking issues');
    cy.getBySelector('cancel-button')
      .should('be.visible');
  });

  it('Test change name', NoRetriesConfig,() => {
    cy.visit(`${settingsProfile}`);
    cy.getBySelector('change-name-link')
      .should('not.be.disabled')
      .click();
    cy.getBySelector('first-name')
      .should('be.visible')
      .clear()
      .type('Abraham');
    cy.getBySelector('last-name')
      .should('be.visible')
      .clear()
      .type('Linconln');
    cy.getBySelector('save-button-name')
      .should('not.be.disabled')
      .click();
    cy.checkSelectorText('alert-text', 'The name has been successfully changed to');
    cy.getBySelector('user-name')
      .should('have.text', 'Abraham Linconln');
  });

  it('Test change email', NoRetriesConfig,() => {
    cy.visit(`${settingsProfile}`);
    cy.getBySelector('change-email-link')
      .should('not.be.disabled')
      .click();
    cy.getBySelector('email-input')
      .should('be.visible')
      .clear()
      .type('max1@gmail.com');
    cy.getBySelector('save-button-change-email')
      .should('not.be.disabled')
      .click();
    cy.checkSelectorText('alert-text', 'Login email address change pending. We\'ve sent a message to max1@gmail.com to confirm this change');
  });

  it('Test change password', NoRetriesConfig,() => {
    const newPassword = 'Craiova2023@';
    cy.setupWaitForCalls(['getWebsitesPerformance']);
    cy.visit(`${settingsProfile}`);
    cy.getBySelector('change-password-link')
      .should('not.be.disabled')
      .click();
    cy.getBySelector('password-input')
      .should('be.visible')
      .clear()
      .type(newPassword);
    cy.getBySelector('password-repeat-input')
      .should('be.visible')
      .clear()
      .type(newPassword);
    cy.getBySelector('save-button-password')
      .should('not.be.disabled')
      .click();
    cy.checkSelectorText('alert-text', 'The password has been successfully changed');
    cy.logout();
    cy.loginWithUI(Users.max, newPassword);
    cy.waitForCall('getWebsitesPerformance');
    cy.getBySelector('projects-table').should('be.visible');
  });

  it('Test two factor authentification', () => {
    // TODO This test should be extendet more
    cy.visit(`${settingsProfile}`);
    cy.getBySelector('two-factor-auth')
      .should('not.be.disabled')
      .click();
    cy.getBySelector('two-factor-auth-modal')
      .should('be.visible');
    cy.getBySelector('next-button')
      .click();
    cy.getBySelector('input-code')
      .should('be.visible')
      .clear()
      .type('123456');
    cy.getBySelector('verify-button')
      .click();
  });
});
