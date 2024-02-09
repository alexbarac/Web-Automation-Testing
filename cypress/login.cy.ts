import { Constants, Users } from '../support/constants';

describe('Login/Logout test suite', () => {
  it('Test Login with wrong password', () => {
    cy.visit('/');
    cy.getBySelector('signin-email')
      .type(Users.max);
    cy.getBySelector('signin-password')
      .type('wrongPassword');
    cy.getBySelector('signin-button')
      .click();
    cy.getBySelector('login-error')
      .should('be.visible');
    cy.url()
      .should('include', '/signin');
  });

  it('Test Login with correct credentials', () => {
    cy.visit('/');
    cy.getBySelector('signin-email')
      .type(Users.maxForUpdates);
    cy.getBySelector('signin-password')
      .type(Constants.maxPassword);
    cy.getBySelector('signin-button')
      .click();
    cy.url()
      .should('eq', `${Cypress.config().baseUrl}/`);
    cy.getCookie('user_data')
      .should('have.property', 'value');
    cy.getBySelector('projects-table').should('be.visible');
  });

  it('Test Sign out', () => {
    cy.login(Users.max, false);
    cy.getBySelector('user-dropdown')
      .should('not.be.disabled')
      .click({ force: true });
    cy.getBySelector('sign-out')
      .should('not.be.disabled')
      .click();
    cy.url()
      .should('eq', `${Cypress.config().baseUrl}/signin`);
    cy.getCookie('user_data')
      .should('not.exist');
  });
});
