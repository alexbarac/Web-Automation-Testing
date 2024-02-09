/// <reference types="cypress" />
/* eslint no-use-before-define: 0 */

import { Urls, Users } from '../support/constants';

describe('Upgrade Plan page test suite', () => {
  // TODO: complete suite
  beforeEach(() => {
    cy.login(Users.max);
  });
  it.skip('Upgrade sanity check', () => {
    cy.visit(`${Urls.accountUpgradeUrl}`);
    cy.getBySelector('pricing-plans')
      .should('be.visible')
      .and('include.text', 'Pay annually to save 10%');
  });
});
