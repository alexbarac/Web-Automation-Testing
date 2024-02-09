import { Urls, Users } from '../support/constants';
import { NoRetriesConfig } from '../support/run-options';

describe('Account settings test suite', () => {
  const firstAdmin = 'joe@gmail.com';
  const secondAdmin = 'kenedy@gmail.com';
  let teamMember = 'donald@gmail.com';
  const adminTable = '[data-cy="admin-table"] > tbody > tr > td:nth-child(2)';
  const teamMemberTable = '[data-cy="team-member-table"] > tbody > tr > td:nth-child(2)';
  const websitesTable = '[data-cy="websites-table"] > tbody > tr > td:nth-child(2)';

  beforeEach(() => {
    cy.login(Users.maxForUpdates);
  });

  function selectRowByText(cellTable, selectedText) {
    cy.get(cellTable)
      .should('contain.text', selectedText)
      .each(($e1, index) => {
        const StoreText = $e1.text();
        if (StoreText.includes(`${selectedText}`)) {
          cy.get(cellTable)
            .eq(index)
            .prev()
            .find('input')
            .click();
        }
      });
  }

  it('Test invite team member user and promote as admin', () => {
    if (Cypress.currentRetry === 1) {
      teamMember = 'test1@gmail.com';
    }
    cy.setupWaitForCalls(['getUsers', 'inviteUsers', 'getAssignedProjects', 'updateUserToProject', 'makeAdmin']);
    cy.visit(`${Urls.accountUsersUrl}`);
    cy.waitForCalls(['getUsers']);
    cy.getBySelector('add-user-button')
      .should('be.enabled')
      .click();
    cy.getBySelector('invite-user-modal')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('team-member-radio-button')
          .should('not.be.disabled')
          .click({ force: true });
        cy.get('#selectize-team-selectized')
          .should('not.be.disabled')
          .clear()
          .type(`${teamMember}{enter}`);
        cy.getBySelector('invite-user-button')
          .should('not.be.disabled')
          .trigger('click');
      });
    cy.waitForCalls(['inviteUsers', 'getAssignedProjects']);
    cy.getBySelector('websites-table')
      .should('be.visible');
    selectRowByText(websitesTable, 'www.allrecipes.com ([AWR Demo Project])');
    cy.getBySelector('assign-website-button')
      .should('not.be.disabled')
      .click();
    cy.waitForCalls(['updateUserToProject', 'getUsers']);
    cy.getBySelector('team-member-table')
      .should('include.text', teamMember);
    // select team member
    selectRowByText(teamMemberTable, teamMember);
    // promote the team member to admin
    cy.getBySelector('promote-admin-button')
      .should('not.be.disabled')
      .click();
    cy.waitForCalls(['makeAdmin', 'getUsers']);
    // verify changes
    cy.getBySelector('admin-table')
      .should('include.text', teamMember);
  });

  it('Test invite admin user and demote it', () => {
    cy.setupWaitForCalls(['getUsers', 'inviteUsers', 'makeTeamMember']);
    cy.visit(`${Urls.accountUsersUrl}`);
    cy.waitForCalls(['getUsers']);
    cy.getBySelector('add-user-button')
      .should('be.enabled')
      .click();
    // add users
    cy.getBySelector('invite-user-modal')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('admin-radio-button')
          .should('be.checked');
        cy.get('#selectize-admin-selectized')
          .should('not.be.disabled')
          .clear()
          .type(`${firstAdmin}{enter}`)
          .type(`${secondAdmin}{enter}`);
        cy.getBySelector('invite-user-button')
          .should('not.be.disabled')
          .click();
      });
    cy.waitForCalls(['inviteUsers', 'getUsers']);
    cy.getBySelector('invite-user-modal')
      .should('not.be.visible');
    // select new admin
    selectRowByText(adminTable, firstAdmin);
    // demote admin to team member
    cy.getBySelector('manage-users-menu')
      .should('be.visible')
      .findBySelector('demote-user-button')
      .should('not.be.disabled')
      .click();
    cy.waitForCalls(['makeTeamMember', 'getUsers']);
    // verify changes
    cy.getBySelector('team-member-table')
      .should('include.text', firstAdmin);
  });

  it('Test edit and delete user', NoRetriesConfig, () => {
    const firstName = 'Joe';
    const lastName = 'Biden';
    cy.setupWaitForCalls(['getUsers', 'updateUserDetails', 'deleteUsers']);
    cy.visit(`${Urls.accountUsersUrl}`);
    cy.waitForCall('getUsers');
    // fill admin details
    cy.get('[data-cy="admin-table"] > tbody > tr > :nth-child(2)')
      .should('be.visible')
      .contains(secondAdmin).each(($el) => {
        $el.find('href');
        cy.wrap($el).click();
      });
    cy.getBySelector('edit-user-modal')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('first-name')
          .clear()
          .type(firstName);
        cy.getBySelector('last-name')
          .clear()
          .type(lastName);
        cy.getBySelector('save-button')
          .should('not.be.disabled')
          .click();
      });
    cy.waitForCalls(['updateUserDetails', 'getUsers']);
    cy.getBySelector('first-name')
      .should('have.value', firstName);
    cy.getBySelector('last-name')
      .should('have.value', lastName);
    // select admin
    selectRowByText(adminTable, secondAdmin);
    // delete admin user
    cy.getBySelector('manage-users-menu')
      .should('be.visible')
      .findBySelector('delete-button')
      .should('not.be.disabled')
      .click();
    cy.waitForCalls(['deleteUsers', 'getUsers']);
    // verify changes
    cy.getBySelector('admin-table')
      .should('not.contain.text', secondAdmin);
  });

  it('Test menus from account settings', () => {
    const settingsMenues = ['Upgrade', 'Billing', 'Users', 'Notes', 'Activity Log'];
    cy.setupWaitForCalls(['getUsers']);
    cy.visit(`${Urls.accountUsersUrl}`);
    cy.waitForCalls(['getUsers']);
    cy.getBySelector('account-menu')
      .should('have.length', 5)
      .each((item, index) => {
        cy.wrap(item)
          .should('contain.text', settingsMenues[index]);
      });
  });
});
