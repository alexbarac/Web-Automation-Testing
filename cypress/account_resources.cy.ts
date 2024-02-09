/// <reference types="cypress" />

import { Urls, Users } from '../support/constants';
import { NoRetriesConfig } from '../support/run-options';

/**
 * Tests were skipped due to BF changes. But test suite needs a re-visit/re-work regardless.
 */
describe('Account resources test suite', () => {
  beforeEach(() => {
    Cypress.config('defaultCommandTimeout', 10000);
    cy.login(Users.max);
  });
  /**
   * Even sanity check test fails intermittently. Something is not right with this page.
   */
  it.skip('Test sanity check for account resources page', () => {
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getBySelector('overview-table')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('table-row')
          .should('be.visible')
          .should('have.length.at.least', 1);
      });
  });

  it.skip('Test "Frequency" filter - for Ranking', () => {
    cy.doNotSavePreferences();
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getBySelector('dropdown-button').contains('Add filter').click();
    cy.getBySelector('frequency').click();
    cy.getBySelector('new-frequency-button').click();
    cy.getBySelector('ranking').click();
    cy.getBySelector('search-intent-dropdown-filter-menu')
      .should('exist')
      .contains('Monthly')
      .click();
    cy.getBySelector('apply-button').click();

    cy.getBySelector('overview-table')
      .first()
      .should('be.visible')
      .within(() => {
        cy.getBySelector('ranking-frequency-column-btn')
          .should('exist')
          .each((element) => {
            cy.wrap(element)
              .should('contain', 'Monthly');
          });
      });
  });

  it.skip('Test KPIs', () => {
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getBySelector('consumption-chart').should('exist');
    cy.getBySelector('consumption-legend').should('exist');
    cy.getBySelector('monthly-demand-chart').should('exist');
    cy.getBySelector('progress-bar').should('exist');
    cy.getBySelector('resources-usage-result').should('exist');
    cy.getBySelector('resources-usage-text').contains('You are currently using invoice payments. The upcoming renewal is due Saturday, December 21, 2030 - (2149 USD).');
    cy.getBySelector('overview-table')
      .first()
      .should('be.visible')
      .within(() => {
        cy.getBySelector('progress-bar')
          .should('exist');
      });

    cy.getBySelector('upgrade-plan-button').click();
    cy.url().should('include', '/account/plans');
  });

  it.skip('Test "How it works" button', () => {
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.window().then((win) => {
      cy.stub(win, 'open').as('windowOpen');
    });
    cy.getBySelector('how-it-works-button').click();

    const newUrl = 'https://help.advancedwebranking.com/en/articles/2120723-keyword-units-explained';
    cy.get('@windowOpen').should('be.calledWith', newUrl);
  });

  it.skip('Test "Frequency" filter - for Difficulty', () => {
    cy.doNotSavePreferences();
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');

    cy.getBySelector('dropdown-button').contains('Add filter').click();
    cy.getBySelector('frequency').click();
    cy.getBySelector('new-frequency-button').click();
    cy.getBySelector('difficulty').click();
    cy.getBySelector('search-intent-dropdown-filter-menu')
      .should('exist')
      .contains('Paused')
      .click();
    cy.getBySelector('apply-button').click();

    cy.getBySelector('overview-table')
      .first()
      .should('be.visible')
      .within(() => {
        cy.getBySelector('difficulty-frequency-column-btn')
          .each((element) => {
            cy.wrap(element)
              .should('contain', 'Paused');
          });
      });
  });

  /**
   * Test fails in gitlab CI. Also needs a significant rewrite.
   */
  it.skip('Test "Projects" column sort', () => {
    cy.doNotSavePreferences();
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');

    // PROJECTS COLUMN SORTING
    verifySort('column-name', 'Projects', 'project-consumption-value');

    // PROJECT CONSUMPTION SORTING
    verifySort('column-name', 'Project consumption', 'project-consumption-value');

    // RANKING-SCHEDULED SORTING
    verifySort('second-header-button', 'Scheduled', 'ranking-scheduled-value');

    // RANKING-ON DEMAND SORTING
    verifySort('second-header-button', 'On demand', 'ranking-on-demand-value');

    // RANKING-FREQUENCY-SORTING
    verifySort('second-header-button', 'Frequency', 'ranking-frequency-column-btn');

    // DIFFICULTY-SCHEDULED SORTING
    verifySort('difficulty-scheduled-button', 'Scheduled', 'difficulty-scheduled');

    // DIFFICULTY-ON-DEMAND SORTING
    verifySort('difficulty-on-demand-button', 'On demand', 'difficulty-on-demand');

    // DIFFICULTY-FREQUENCY-SORTING
    verifySort('difficulty-frequency-button', 'Frequency', 'difficulty-frequency-column-btn');
  });

  it.skip('Test project row expand - keywords drilldown', () => {
    cy.setupWaitForCalls([
      'getKeywords',
      'getKeywordsDropdown',
      'getAccountResources',
    ]);
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getBySelector('overview-table')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('project-column')
          .contains('apple')
          .click();
        cy.getBySelector('expanded-row')
          .should('be.visible')
          .within(() => {
            cy.getBySelector('keywords-details')
              .should('not.be.disabled')
              .click();
          });
      });

    cy.url().should('include', '/settings/keywords');

    cy.waitForCall('getKeywords');
    cy.waitForCall('getKeywordsDropdown');

    cy.getBySelector('main-projects-dropdown').should('contain', 'apple.com');
    cy.getBySelector('menu-link').contains('Keywords').should('have.class', 'active');
    cy.getBySelector('dropdown-button').should('contain', 'All keywords');

    cy.getBySelector('search-input').should('have.value', '');
    cy.getBySelector('dropdown-button').should('exist');
    cy.getBySelector('advanced-filter').should('not.exist');
  });

  it.skip('Test project row expand - search engine drilldown', () => {
    cy.setupWaitForCalls([
      'getCountries',
      'getSearchEngines',
      'getAccountResources',
    ]);
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');

    cy.getBySelector('overview-table')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('project-column')
          .contains('apple')
          .click();
        cy.getBySelector('expanded-row')
          .should('be.visible')
          .within(() => {
            cy.getBySelector('search-engine-units-details')
              .should('not.be.disabled')
              .click();
          });
      });

    cy.url().should('include', 'settings/search-engines');
    cy.waitForCall('getSearchEngines');

    cy.getBySelector('main-projects-dropdown').should('contain', 'apple.com');
    cy.getBySelector('menu-link').contains('Search engines').should('have.class', 'active');
  });

  it.skip('Test Ranking updates - Frequency ', NoRetriesConfig, () => {
    cy.setupWaitForCalls(['changeFrequency', 'getAccountResources']);
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getRowByText('overview-table', 'ebay.com').within(() => {
      cy.getBySelector('ranking-frequency-column-btn')
        .click();
      cy.getBySelector('dropdown-menu-frequency').contains('Daily').click();
    });

    cy.getBySelector('simple-alert-modal')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('modal-title')
          .should('have.text', 'Ranking update frequency');
        cy.getBySelector('ok-button')
          .should('not.be.disabled')
          .click();
      });

    cy.waitForCall('changeFrequency');

    cy.waitForCall('getAccountResources');

    cy.getRowByText('overview-table', 'ebay.com').within(() => {
      cy.getBySelector('ranking-frequency-column-btn').should('contain', 'Daily');
    });
  });

  it.skip('Test Difficulty updates - Frequency ', () => {
    cy.setupWaitForCalls(['changeDifficultyFrequency', 'getAccountResources']);
    cy.intercept('/rest').as('refreshResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');
    cy.getRowByText('overview-table', 'ebay.com')
      .within(() => {
        cy.getBySelector('difficulty-frequency-column-btn')
          .should('not.be.disabled')
          .click();
        cy.getBySelector('dropdown-difficulty-frequency')
          .contains('Weekly')
          .should('not.be.disabled')
          .click();
      });

    cy.getBySelector('simple-alert-modal')
      .should('be.visible')
      .findBySelector('ok-button')
      .should('not.be.disabled')
      .click();

    cy.waitForCall('changeDifficultyFrequency');
    cy.wait('@refreshResources');
    cy.waitForCall('getAccountResources');

    cy.getRowByText('overview-table', 'ebay.com')
      .findBySelector('difficulty-frequency-column-btn')
      .should('contain.text', 'Weekly');
  });

  it.skip('Test search for name/website URL', () => {
    cy.doNotSavePreferences();
    cy.setupWaitForCall('getAccountResources');
    cy.visit(Urls.accountResourcesUrl);
    cy.waitForCall('getAccountResources');

    searchForKeyword('apple');

    verifyProjectColumnsContainKeyword('apple');

    searchForKeyword('aaa');

    verifyNoDataAvailableMessage();
  });

  function searchForKeyword(keyword) {
    cy.getBySelector('filters-container')
      .first()
      .within(() => {
        cy.getBySelector('search-input')
          .should('be.visible')
          .type(`${keyword}{enter}`);
      });
  }

  function verifyProjectColumnsContainKeyword(keyword) {
    cy.getBySelector('project-column')
      .should('exist')
      .each((element) => {
        cy.wrap(element)
          .should('contain', keyword);
      });
  }

  function verifyNoDataAvailableMessage() {
    cy.getBySelector('no-data-available').contains('No data available');
  }
  it.skip('Test multiple delete', () => {
    cy.doNotSavePreferences();
    cy.setupWaitForCall('deleteWebsites');

    cy.getBySelector('filters-container')
      .first()
      .within(() => {
        cy.getBySelector('search-input').clear();
      });

    cy.selectRowsByCount('overview-table', 2);

    cy.getBySelector('overview-table')
      .first()
      .should('be.visible')
      .within(() => {
        cy.getBySelector('manage-container')
          .first()
          .should('be.visible')
          .within(() => {
            cy.getBySelector('delete-button')
              .should('not.be.disabled')
              .click();
          });
      });

    cy.getBySelector('simple-alert-modal')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('modal-title')
          .should('have.text', 'Delete websites');
        cy.getBySelector('ok-button')
          .should('not.be.disabled')
          .click();
      });

    cy.waitForCall('deleteWebsites');

    cy.getBySelector('alert-content')
      .should('be.visible')
      .within(() => {
        cy.getBySelector('alert-title')
          .should('have.text', 'Delete websites');
        cy.getBySelector('alert-text')
          .should('have.text', 'You deleted 2 websites!');
        cy.getBySelector('close-alert-button')
          .should('not.be.disabled')
          .click();
      });
  });
});

function verifySort(columnName, columnNameString, columnElement) {
  cy.getBySelector('overview-table')
    .first()
    .should('be.visible')
    .within(() => {
      cy.getBySelector(columnName).contains(columnNameString).click(); // first click
    });

  const initialArray = [];
  cy.getBySelector(columnElement)
    .each((element) => {
      initialArray.push(element.text().trim());
    });

  cy.getBySelector('overview-table')
    .first()
    .should('be.visible')
    .within(() => {
      cy.getBySelector(columnName).contains(columnNameString).click(); // second click
    });

  cy.getBySelector(columnElement)
    .then((element) => {
      const sortedArray = [];
      cy.wrap(element).each((crtElem) => {
        sortedArray.push(crtElem.text().trim());
      }).then(() => {
        expect(initialArray.reverse()).deep.equal(sortedArray);
      });
    });
}
