/// <reference types="cypress" />
/* eslint no-use-before-define: 0 */

import { PreferencesKeys, projectIDs, Users } from '../support/constants';
import { CypressImageDiffConfig, NoRetriesConfig } from '../support/run-options';

describe('Home Page test suite', () => {
  beforeEach(() => {
    if (Cypress.currentTest.title === 'Test Delete Project'
      || Cypress.currentTest.title === 'Test Duplicate Project') {
      cy.login(Users.maxForUpdates);
    } else {
      cy.login(Users.max);
    }
  });

  afterEach(() => {
    // restore: this is run only if initialPreferences alias exists
    if (cy.state('aliases')?.initialPreferences) {
      cy.get('@initialPreferences')
        .then((preferences) => {
          cy.restoreAccountPreferences(preferences, PreferencesKeys.homePage);
          cy.restoreAccountPreferences(preferences.dateRangeType, PreferencesKeys.homePageDate);
          // eslint-disable-next-line max-len
          cy.restoreAccountPreferences(preferences, PreferencesKeys.homePageKpi);
        });
    }
  });

  const table = 'websites-table';

  describe('Drilldowns tests', () => {
    it('Test the first kpi drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getKeywordGroups', 'getVisibilityWebsitesTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('first-kpi-container')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'visibility/websites');

      cy.waitForCalls(['getKeywordGroups', 'getVisibilityWebsitesTable']);
      cy.getBySelector('main-projects-dropdown')
        .should('include.text', 'advancedwebranking_MAIN');
      cy.getBySelector('keyword-groups-dropdown')
        .should('include.text', 'All keywords');
      cy.getBySelector('expanded-table-row')
        .should('be.visible');
      // assert the KPIs chart was expended by closing it
      cy.getBySelector('table-row')
        .contains('Visibility Percent')
        .should('be.visible')
        .click();
      cy.getBySelector('expanded-table-row')
        .should('not.exist');
    });

    it('Test the second kpi drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getKeywordGroupsDropdown', 'getSearchEnginesComparisonTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('second-kpi-container')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'comparison/search-engines');

      cy.waitForCalls(['getKeywordGroupsDropdown', 'getSearchEnginesComparisonTable']);
      cy.getBySelector('main-projects-dropdown')
        .should('include.text', 'advancedwebranking_MAIN');
      cy.getBySelector('dropdown-button')
        .should('include.text', 'All keywords');
      cy.getBySelector('position-filter')
        .should('contain.text', '1 - 100');
    });

    it('Test the third kpi drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getKeywordGroups', 'getVisibilityWebsitesTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('third-kpi-container')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'visibility/websites');

      cy.waitForCalls(['getKeywordGroups', 'getVisibilityWebsitesTable']);
      cy.getBySelector('main-projects-dropdown')
        .should('include.text', 'advancedwebranking_MAIN');
      cy.getBySelector('keyword-groups-dropdown')
        .should('include.text', 'All keywords');
      cy.getBySelector('expanded-table-row')
        .should('be.visible');
      cy.getBySelector('table-row')
        .contains('Average Rank')
        .should('be.visible')
        .click();
      cy.getBySelector('expanded-table-row')
        .should('not.exist');
    });

    it('Test the forth kpi drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getKeywordGroups', 'getVisibilityWebsitesTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('forth-kpi-container')
        .should('not.be.disabled')
        .click();

      cy.waitForCalls(['getKeywordGroups', 'getVisibilityWebsitesTable']);

      cy.url().should('contain', 'visibility/websites');
      cy.getBySelector('main-projects-dropdown')
        .should('include.text', 'advancedwebranking_MAIN');
      cy.getBySelector('keyword-groups-dropdown')
        .should('include.text', 'All keywords');

      cy.getBySelector('expanded-table-row')
        .should('be.visible');
      cy.getBySelector('table-row')
        .contains('Click Share')
        .should('not.be.disabled')
        .click();
      cy.getBySelector('expanded-table-row')
        .should('not.exist');
    });

    it('Test Top Performers KPI - Best Group drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines', 'getVisibilityKeywordGroupsTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('top-performers-kpi')
        .should('include.text', 'Top Performers')
        .findBySelector('best-group')
        .should('include.text', 'Branded')
        .and('not.be.disabled')
        .click();
      cy.url().should('contain', 'visibility/groups');

      cy.waitForCalls(['getSearchEngines', 'getVisibilityKeywordGroupsTable']);

      cy.checkSelectorText('dropdown-project-button', 'advancedwebranking_MAIN');
      cy.checkSelectorText('search-engines-dropdown', 'Desktop');
      cy.getBySelector('table-row')
        .should('contain.text', 'Branded');
    });

    it('Test Top Performers KPI - Best Page drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines', 'getLandingPageTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('top-performers-kpi')
        .should('include.text', 'Top Performers')
        .findBySelector('best-page')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'visibility/landing-pages');

      cy.waitForCalls(['getSearchEngines', 'getLandingPageTable']);

      cy.checkSelectorText('dropdown-project-button', 'advancedwebranking_MAIN');
      cy.checkSelectorText('search-engines-dropdown', 'Desktop');
      cy.getBySelector('landing-pages-table')
        .findBySelector('table-row')
        .should('be.visible')
        .and('include.text', 'advancedwebranking');
    });

    it('Test Top Performers KPI - Top Keyword drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines', 'getKeywordsRankingTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('top-performers-kpi')
        .should('include.text', 'Top Performers')
        .findBySelector('top-keyword')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'ranking/keywords');

      cy.waitForCalls(['getSearchEngines', 'getKeywordsRankingTable']);

      cy.checkSelectorText('dropdown-project-button', 'advancedwebranking_MAIN');
      cy.checkSelectorText('search-engines-dropdown', 'Desktop');
      cy.getBySelector('keywords-table')
        .findBySelector('table-row')
        .should('be.visible')
        .and('include.text', 'advancedwebranking');
    });

    it('Test Position Distribution KPI - First place drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEnginesComparisonTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'apple.com')
        .findBySelector('position-distribution-content')
        .should('include.text', 'Position Distribution')
        .findBySelector('first-place')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'comparison/search-engines');
      cy.waitForCalls(['getSearchEnginesComparisonTable']);

      cy.checkSelectorText('dropdown-project-button', 'apple');
      cy.checkSelectorText('table-row', '1');
      cy.checkSelectorText('column-name', 'Desktop');
    });

    it('Test top position distribution - 2-5 position drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEnginesComparisonTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'apple.com')
        .findBySelector('position-distribution-content')
        .should('include.text', 'Position Distribution')
        .findBySelector('second-five-place')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'comparison/search-engines');

      cy.waitForCalls(['getSearchEnginesComparisonTable']);

      cy.checkSelectorText('main-projects-dropdown', 'apple');
      cy.checkSelectorText('table-row', '2');
      cy.checkSelectorText('table-row', '3');
      cy.checkSelectorText('column-name', 'Desktop');
    });

    it('Test top position distribution - Pos. #6-10 drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEnginesComparisonTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'apple.com')
        .findBySelector('position-distribution-content')
        .should('include.text', 'Position Distribution')
        .findBySelector('six-ten-place')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'comparison/search-engines');

      cy.waitForCalls(['getSearchEnginesComparisonTable']);
      cy.checkSelectorText('main-projects-dropdown', 'apple');
      cy.checkSelectorText('table-row', '7');
      cy.checkSelectorText('column-name', 'Desktop');
    });

    it('Test top position distribution - Pos. #11-20 drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEnginesComparisonTable']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('performance')
        .should('not.be.disabled')
        .click();

      cy.getRowByText(table, 'apple.com')
        .findBySelector('position-distribution-content')
        .should('include.text', 'Position Distribution')
        .findBySelector('eleven-twenty-place')
        .should('not.be.disabled')
        .click();

      cy.url().should('contain', 'comparison/search-engines');

      cy.waitForCalls(['getSearchEnginesComparisonTable']);
      cy.checkSelectorText('main-projects-dropdown', 'apple');
      cy.checkSelectorText('table-row', '12');
      cy.checkSelectorText('column-name', 'Desktop');
    });

    it('Test page depth drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'getPartialUpdateAlert']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');

      cy.getRowByText('projects-table', 'apple_CRUD')
        .findBySelector('page-depth-button')
        .should('not.be.disabled')
        .click();

      cy.waitForCall('getPartialUpdateAlert');
      cy.url().should('contain', 'overview?project_id');
      cy.getBySelector('websites-dropdown')
        .should('include.text', 'apple.com');
    });

    it('Test Search engines drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'getPartialUpdateAlert', 'getSearchEngines']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');

      cy.getRowByText('projects-table', 'apple_CRUD')
        .findBySelector('search-engine-grey-button')
        .should('not.be.disabled')
        .click();
      cy.waitForCalls(['getPartialUpdateAlert', 'getSearchEngines']);

      cy.url().should('contain', 'search-engines?');

      cy.getBySelector('ses-table')
        .findBySelector('table-row')
        .should('include.text', 'Mobile')
        .and('include.text', 'Desktop')
        .and('include.text', 'Yahoo');
    });

    it('Test keywords drilldown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'getPartialUpdateAlert']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');

      cy.getRowByText('projects-table', 'apple_CRUD')
        .findBySelector('keywords-grey-button')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getPartialUpdateAlert');

      cy.url().should('contain', 'keywords?project_id');
      cy.getBySelector('menu-link')
        .contains('Keywords')
        .should('have.class', 'active');
    });
  });

  describe('Performance tab', () => {
    it('Test change the project frequency', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'changeFrequency']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      // daily freq
      cy.getRowByText(table, 'apple.com')
        .selectFrequency('project-frequency-dropdown', 'Daily');
      cy.waitForCalls(['changeFrequency', 'getWebsitesPerformance']);
      cy.getRowByText(table, 'apple.com')
        .findBySelector('project-frequency-dropdown')
        .should('contain', 'Daily');

      // weekly freq
      cy.getRowByText(table, 'apple.com')
        .selectFrequency('project-frequency-dropdown', 'Weekly');
      cy.waitForCalls(['changeFrequency', 'getWebsitesPerformance']);
      cy.getRowByText(table, 'apple.com')
        .findBySelector('project-frequency-dropdown')
        .should('contain', 'Weekly');

      // biweekly freq
      cy.getRowByText(table, 'apple.com')
        .selectFrequency('project-frequency-dropdown', 'Biweekly');
      cy.waitForCalls(['changeFrequency', 'getWebsitesPerformance']);
      cy.getRowByText(table, 'apple.com')
        .findBySelector('project-frequency-dropdown')
        .should('contain', 'Biweekly');

      // reset the test
      cy.getRowByText(table, 'apple.com')
        .selectFrequency('project-frequency-dropdown', 'Monthly');
      cy.waitForCalls(['changeFrequency', 'getWebsitesPerformance']);
    });

    it('Test On demand button', NoRetriesConfig, () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'EstimateOnDemandUpdate', 'ondemandUpdate']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getRowByText(table, 'apple_CRUD')
        .findBySelector('project-frequency-dropdown')
        .within(() => {
          cy.getBySelector('dropdown-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('dropdown-menu')
            .contains('Run on demand')
            .should('not.be.disabled')
            .click();
        });

      cy.getBySelector('ondemand-modal')
        .should('be.visible')
        .within(() => {
          cy.getBySelector('check-difficulty')
            .check({ force: true });
          cy.getBySelector('update-button')
            .should('not.be.disabled')
            .click();
        });

      cy.waitForCall('ondemandUpdate');

      cy.getRowByText(table, 'apple_CRUD')
        .findBySelector('project-frequency-dropdown')
        .within(() => {
          cy.getBySelector('dropdown-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('dropdown-menu')
            .contains('Run on demand')
            .should('not.be.disabled')
            .click();
        });

      cy.getBySelector('ondemand-modal')
        .should('be.visible')
        .within(() => {
          cy.getBySelector('update-button')
            .should('be.disabled');
          cy.getBySelector('alert-content')
            .should('be.visible');
          cy.checkSelectorText('alert-content', 'An update is already in progress.');
        });
    });

    it('Test filter projects dropdown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getProjectFilterDropdown']);
      cy.intercept('/rest').as('savePref');
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.storePreferences();
      cy.getBySelector('project-filter-dropdown')
        .within(() => {
          cy.getBySelector('dropdown-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('dropdown-menu')
            .should('be.visible')
            .contains('Paused')
            .should('not.be.disabled')
            .click();
        });
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.wait('@savePref');

      cy.reload();
      cy.waitForCalls(['getProjectFilterDropdown', 'getWebsitesPerformance']);

      cy.checkSelectorText('project-filter-dropdown', 'Paused');
      cy.getBySelector(table)
        .findBySelector('project-frequency-dropdown')
        .each((rowTitle) => {
          cy.wrap(rowTitle)
            .should('contain.text', 'Paused');
        });
    });

    it('Test date dropdown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.intercept('/rest').as('savePref');
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.storePreferences();
      cy.getBySelector('performance').click();
      cy.getBySelector('time-filter-container')
        .findBySelector('dropdown-button')
        .should('include.text', 'Last month');

      cy.selectDropdownOption('time-filter-container', 'Last year');

      cy.waitForCall('getWebsitesPerformance');
      cy.wait('@savePref');

      cy.getBySelector('time-filter-container')
        .findBySelector('dropdown-button')
        .should('include.text', 'Last year');

      cy.reload();
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getBySelector('time-filter-container')
        .findBySelector('dropdown-button')
        .should('include.text', 'Last year');
    });

    it('Test Export button', () => {
      const buttons = [
        'export-pdf-button',
        'export-csv-button',
        'export-xlsx-button',
        'upload-google-drive-pdf',
        'upload-google-drive-csv',
      ];
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getBySelector('export-button')
        .should('not.be.disabled')
        .click();
      buttons.forEach((button) => {
        cy.getBySelector(button)
          .should('be.visible')
          .and('not.be.disabled');
      });
    });

    it('Test project searchbar', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('search-filter')
        .should('not.be.disabled')
        .clear()
        .type('apple{enter}');

      cy.waitForCall('getWebsitesPerformance');

      cy.getBySelector(table)
        .findBySelector('project-title-management')
        .each((el) => cy.wrap(el).should('contain.text', 'apple'));
    });

    it('Test the search engine button', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getRowByText(table, 'advancedwebranking_MAIN').within(() => {
        cy.selectDropdownOption('search-engines-dropdown', 'Google.com Mobile Universal');
      });

      cy.waitForCalls(['getWebsitesPerformance', 'getSearchEngines']);
      cy.getRowByText(table, 'apple.com')
        .findBySelector('search-engines-dropdown')
        .should('contain.text', 'Mobile');
    });

    it('Test share button', () => {
      cy.setupWaitForCalls([
        'getProjectPermalink',
        'getWebsitesPerformance',
      ]);
      cy.visit('/');
      cy.waitForCall('getWebsitesPerformance');

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('share-link-button')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getProjectPermalink');
      cy.getBySelector('share-project-modal')
        .should('be.visible')
        .within(() => {
          cy.getBySelector('share-via-link-session-name')
            .should('have.value', 'Keywords Ranking');
          cy.getBySelector('selected-project-name')
            .should('contain.text', 'advancedwebranking_MAIN');
          cy.getBySelector('selected-section-name')
            .should('contain.text', 'Keywords Ranking');
          cy.getBySelector('share-link-modal-done-button')
            .should('not.be.disabled');
        });
    });

    it('Test option button: settings', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getRowByText(table, 'apple.com')
        .within(() => {
          cy.getBySelector('options-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('options-menu')
            .should('be.visible')
            .contains('settings')
            .should('not.be.disabled')
            .click();
        });

      cy.url().should('contain', 'settings/overview');
      cy.getBySelector('websites-dropdown')
        .should('include.text', 'apple.com');
    });

    it('Test option button: duplicate', () => {
      cy.setupWaitForCalls(['duplicateProjects', 'getWebsitesPerformance']);
      cy.visit('/');
      cy.waitForCall('getWebsitesPerformance');

      cy.getRowByText(table, '[AWR Demo Project]')
        .within(() => {
          cy.getBySelector('options-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('options-menu')
            .should('be.visible')
            .contains('Duplicate')
            .should('not.be.disabled')
            .click();
        });

      cy.getBySelector('websites-table-performance')
        .findBySelector('duplicate-modal')
        .should('be.visible')
        .and('contain.text', 'Duplicate Projects');
    });

    it('Test option button: delete', () => {
      cy.setupWaitForCalls(['deleteWebsites', 'getWebsitesPerformance']);
      cy.visit('/');
      cy.waitForCall('getWebsitesPerformance');

      cy.getRowByText(table, '[AWR Demo Project]')
        .within(() => {
          cy.getBySelector('options-button')
            .should('not.be.disabled')
            .click();
          cy.getBySelector('options-menu')
            .should('be.visible')
            .contains('Delete')
            .should('not.be.disabled')
            .click();
        });

      cy.getBySelector('simple-alert-modal')
        .should('be.visible')
        .and('contain.text', 'Delete websites')
        .findBySelector('ok-button')
        .should('be.visible')
        .and('not.be.disabled');
    });

    it('Test the KPIs dropdowns', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      checkDropdownKpis(['first', 'second', 'third', 'forth'], table, 'apple.com', 'Top 30');
    });

    it('Test KPIs dropdown preferences', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.intercept('/rest').as('savePref');
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.storePreferences();

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .within(() => {
          cy.selectDropdownOption('first-kpi-container', 'Not Ranked');
        });

      cy.wait('@savePref');
      cy.reload();
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getRowByText(table, 'advancedwebranking_MAIN')
        .findBySelector('first-kpi-container')
        .should('be.visible')
        .findBySelector('dropdown-button')
        .should('include.text', 'Not Ranked');
    });

    it('Test Keyword Difficulty dropdown', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'changeDifficultyFrequency']);
      cy.doNotSavePreferences('stubbedRequest');
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getRowByText(table, 'apple.com')
        .selectFrequency('difficulty-frequency-dropdown', 'Weekly');

      cy.getBySelector('simple-alert-modal')
        .should('be.visible')
        .findBySelector('ok-button')
        .should('not.be.disabled')
        .click();

      cy.waitForCalls(['changeDifficultyFrequency', 'getWebsitesPerformance']);
      cy.wait('@stubbedRequest');

      cy.getRowByText(table, 'apple.com')
        .within(() => {
          cy.checkSelectorText('difficulty-frequency-dropdown', 'Weekly');
        });
    });

    it('Test Keyword Difficulty Run on demand', NoRetriesConfig, () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'EstimateKeywordDifficultyUpdate', 'changeDifficultyFrequency']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.getRowByText(table, 'apple_CRUD')
        .within(() => {
          cy.getBySelector('difficulty-frequency-dropdown')
            .should('not.be.disabled')
            .within(() => {
              cy.getBySelector('dropdown-button')
                .should('not.be.disabled')
                .click();
              cy.getBySelector('dropdown-menu')
                .should('be.visible')
                .contains('Run on demand update')
                .should('not.be.disabled')
                .click();
            });
          cy.waitForCall('EstimateKeywordDifficultyUpdate');

          cy.getBySelector('keyword-difficulty-update-modal')
            .should('be.visible')
            .findBySelector('difficulty-update-button')
            .should('be.visible');
        });
    });
    describe('Multiple selection manage bar', () => {
      it('Test Duplicate Project', NoRetriesConfig, () => {
        cy.setupWaitForCalls(['duplicateProjects', 'getWebsitesPerformance']);
        cy.visit('/');
        // cy.waitForCall('getWebsitesPerformance');

        cy.getRowByText(table, '[AWR Demo Project]')
          .findBySelector('websites-table-row-select-checkbox')
          .should('not.be.disabled')
          .check({ force: true });

        cy.getBySelector('manage-container')
          .should('exist')
          .findBySelector('duplicate-project-button')
          .should('not.be.disabled')
          .click();

        cy.getBySelector('duplicate-modal')
          .filter(':visible')
          .should('contain.text', 'Duplicate Projects')
          .within(() => {
            cy.getBySelector('new-project-input')
              .should('not.be.disabled')
              .clear()
              .type('New project 2');
            cy.getBySelector('duplicate-project-action-button')
              .should('not.be.disabled')
              .click();
          });

        cy.waitForCall('duplicateProjects');

        cy.getBySelector('alert-content')
          .should('be.visible')
          .and('include.text', 'Duplicate Projects')
          .and('include.text', 'Your duplicated project will be available shortly.');
      });
      it('Test Add Tag', NoRetriesConfig, () => {
        cy.setupWaitForCalls(['getWebsitesPerformance', 'addProjectsTag', 'getProjectTags', 'assignToProjectTags']);
        cy.doNotSavePreferences();
        cy.visit('/');
        cy.waitForCalls(['getWebsitesPerformance']);
        cy.getBySelector('performance').click();

        cy.getRowByText(table, 'apple_CRUD')
          .findBySelector('websites-table-row-select-checkbox')
          .should('not.be.disabled')
          .check({ force: true });

        cy.getBySelector('manage-container')
          .should('be.visible')
          .findBySelector('TagProjectsButton')
          .should('not.be.disabled')
          .click();

        cy.getBySelector('assign-to-modal-content')
          .should('be.visible')
          .within(() => {
            cy.getBySelector('add-new-button')
              .should('not.be.disabled')
              .click();
            cy.getBySelector('add-new-input')
              .should('not.be.disabled')
              .type('NewTag01');
            cy.getBySelector('input-add-button')
              .should('not.be.disabled')
              .click();

            cy.waitForCalls(['addProjectsTag', 'getWebsitesPerformance', 'getProjectTags']);

            cy.getBySelector('dynamic-alert')
              .should('exist')
              .findBySelector('alert-content')
              .should('be.visible')
              .within(() => {
                cy.getBySelector('alert-title')
                  .should('have.text', 'Tags');
                cy.getBySelector('alert-text')
                  .should('have.text', 'You added 1 new tag!');
              });
            cy.getBySelector('save-button')
              .should('not.be.disabled')
              .click();
          });
        cy.waitForCall('assignToProjectTags');

        cy.getBySelector('assign-to-modal-content')
          .should('not.be.visible');

        cy.getBySelector('project-filter-dropdown')
          .findBySelector('dropdown-button')
          .should('not.be.disabled')
          .click();

        cy.getBySelector('filter-menu-tags')
          .should('be.visible')
          .within(() => {
            cy.contains('NewTag01')
              .should('exist');
          });
      });

      it('Test Delete Project', () => {
        const projectToDelete = 'amazon.com_to_be_deleted_from_home';
        cy.setupWaitForCalls(['getWebsitesPerformance', 'deleteWebsites', 'getProjectsDropdown', 'getProjectTags']);
        // visit with project id (Demo Project)
        // is necessary to prevent default redirect
        // to keywords ranking after deletion
        cy.visit(`/?project_id=${projectIDs.AWR_DEMO}`);
        cy.waitForCalls(['getWebsitesPerformance']);
        // select project
        cy.getRowByText(table, projectToDelete)
          .findBySelector('websites-table-row-select-checkbox')
          .should('not.be.disabled')
          .check({ force: true });
        // delete project
        cy.getBySelector('manage-container')
          .should('exist')
          .findBySelector('delete-button')
          .should('not.be.disabled')
          .click();
        cy.getBySelector('simple-alert-modal')
          .should('be.visible')
          .and('contain.text', 'Delete websites')
          .findBySelector('ok-button')
          .should('not.be.disabled')
          .click();
        cy.waitForCall('deleteWebsites');
        // verify changes
        cy.getBySelector('input-change-alert')
          .should('be.visible')
          .findBySelector('alert-content')
          .should('contain.text', 'You deleted 1 website!');
        cy.waitForCall('getWebsitesPerformance');
        cy.getBySelector(table)
          .should('be.visible')
          .and('not.contain.text', projectToDelete);
      });
    });
  });

  describe('Management tab', () => {
    it('Test columns sorting - project column', () => {
      const initialArray = [];
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
      cy.doNotSavePreferences('stubbedRequest');
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');

      cy.getBySelector('project-title-management')
        .each((element) => initialArray.push(element.text().trim()));
      cy.getBySelector(table)
        .findBySelector('column-name')
        .contains('Project')
        .should('not.be.disabled')
        .click();

      cy.waitForCall('getWebsitesManagement');
      cy.wait('@stubbedRequest');

      cy.getBySelector('project-title-management').then((elem) => {
        const sortedArray = [];
        cy.wrap(elem)
          .each((crtElem) => sortedArray.push(crtElem.text().trim()))
          .then(() => expect(initialArray.reverse()).deep.equal(sortedArray));
      });
    });

    it('Test columns sorting - ranking frequency', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
      cy.doNotSavePreferences('stubbedRequest');
      cy.visit('/');
      cy.waitForCall('getWebsitesPerformance');
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');
      // check initial row, before sort
      cy.getBySelector(table)
        .should('be.visible')
        .findBySelector('table-row')
        .first()
        .within(() => {
          cy.getBySelector('project-title-management')
            .should('contain.text', 'advancedwebranking_MAIN');
          cy.getBySelector('project-frequency-dropdown')
            .should('be.visible')
            .findBySelector('dropdown-button')
            .should('contain.text', 'Monthly');
        });
      // sort by ranking frequency
      cy.getBySelector(table)
        .should('be.visible')
        .findBySelector('column-name')
        .contains('Ranking Frequency')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.wait('@stubbedRequest');
      // verify changes
      cy.getBySelector(table)
        .should('be.visible')
        .findBySelector('table-row')
        .first()
        .within(() => {
          cy.getBySelector('project-title-management')
            .should('contain.text', '[AWR Demo Project]');
          cy.getBySelector('project-frequency-dropdown')
            .should('be.visible')
            .findBySelector('dropdown-button')
            .should('contain.text', 'Paused');
        });
    });

    it('Test the ranking frequency drop-down', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'changeFrequency']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      // switch to management tab
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');
      // change frequency
      cy.getRowByText('projects-table', 'apple.com_1')
        .selectFrequency('project-frequency-dropdown', 'Monthly');
      cy.waitForCalls(['changeFrequency', 'getWebsitesManagement']);
      // test if button set to new value
      cy.getRowByText('projects-table', 'apple.com_1')
        .findBySelector('project-frequency-dropdown')
        .should('include.text', 'Monthly');
    });

    it('Test the difficulty frequency drop-down', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'changeDifficultyFrequency']);
      cy.doNotSavePreferences('stubbedRequest');
      cy.visit('/');
      cy.waitForCall('getWebsitesPerformance');
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.wait('@stubbedRequest');
      cy.getBySelector('management')
        .should('have.class', 'active');
      // change frequency
      cy.getRowByText('projects-table', 'apple.com_1')
        .selectFrequency('difficulty-frequency-dropdown', 'Monthly', { force: true });
      cy.waitForCalls(['changeDifficultyFrequency', 'getWebsitesManagement']);
      cy.wait('@stubbedRequest');
      // verify changes
      cy.getRowByText('projects-table', 'apple.com_1')
        .findBySelector('difficulty-frequency-dropdown')
        .should('contain.text', 'Monthly');
    });

    it('Test connectors settings button', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'getAlertTriggers']);
      cy.doNotSavePreferences();
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.getBySelector('management')
        .should('not.be.disabled')
        .click();
      cy.waitForCall('getWebsitesManagement');
      cy.getBySelector('management')
        .should('have.class', 'active');

      cy.getRowByText('projects-table', 'apple.com_1')
        .findBySelector('google-settings-button')
        .should('not.be.disabled')
        .click();

      cy.waitForCall('getAlertTriggers');
      cy.url().should('contain', 'settings/google-connect?project_id');
    });

    describe('Actions Column', () => {
      it('Test Duplicate Project Button', () => {
        cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
        cy.doNotSavePreferences();
        cy.visit('/');
        cy.waitForCalls(['getWebsitesPerformance']);
        cy.getBySelector('management')
          .should('not.be.disabled').click();
        cy.waitForCall('getWebsitesManagement');
        cy.getBySelector('management')
          .should('have.class', 'active');
        cy.getRowByText('projects-table', '[AWR Demo Project]')
          .findBySelector('duplicate-button-actions')
          .should('not.be.disabled')
          .click();

        cy.getBySelector('duplicate-modal')
          .should('be.visible')
          .and('include.text', 'Duplicate Projects');
      });

      it('Test Share Button', () => {
        cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'getProjectPermalink']);
        cy.doNotSavePreferences();
        cy.visit('/');
        cy.waitForCalls(['getWebsitesPerformance']);
        cy.getBySelector('management').click();
        cy.waitForCall('getWebsitesManagement');
        cy.getBySelector('management').should('have.class', 'active');

        cy.getRowByText(table, 'advancedwebranking_MAIN')
          .findBySelector('share-link-button')
          .should('not.be.disabled')
          .click();
        cy.waitForCall('getProjectPermalink');
        cy.getBySelector('share-project-modal')
          .should('be.visible')
          .within(() => {
            cy.getBySelector('share-via-link-session-name')
              .should('have.value', 'Keywords Ranking');
            cy.getBySelector('selected-project-name')
              .should('contain.text', 'advancedwebranking_MAIN');
            cy.getBySelector('selected-section-name')
              .should('contain.text', 'Keywords Ranking');
            cy.getBySelector('share-link-modal-done-button')
              .should('not.be.disabled');
          });
      });

      it('Test Settings Button', () => {
        cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
        cy.doNotSavePreferences();
        cy.visit('/');
        cy.waitForCalls(['getWebsitesPerformance']);
        cy.getBySelector('management')
          .should('not.be.disabled')
          .click();
        cy.waitForCall('getWebsitesManagement');
        cy.getBySelector('management').should('have.class', 'active');

        cy.getRowByText('projects-table', 'apple.com_1')
          .findBySelector('settings-action-button')
          .should('not.be.disabled')
          .click();

        cy.url().should('contain', 'settings/overview?project_id');
        cy.getBySelector('websites-dropdown')
          .should('include.text', 'apple.com');
      });

      it('Test Email Updates Drop-down', () => {
        cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement', 'updateEmailFrequency']);
        cy.doNotSavePreferences();
        cy.visit('/');
        cy.waitForCall('getWebsitesPerformance');
        cy.getBySelector('management')
          .should('not.be.disabled')
          .click();
        cy.waitForCall('getWebsitesManagement');
        cy.getBySelector('management')
          .should('have.class', 'active');
        // change email updates frequency
        cy.getRowByText('projects-table', '[AWR Demo Project]')
          .within(() => {
            cy.getBySelector('email-updates-dropdown')
              .findBySelector('dropdown-button')
              .should('contain.text', 'Never')
              .and('not.be.disabled')
              .click();

            cy.getBySelector('dropdown-menu')
              .should('be.visible')
              .findBySelector('dropdown-item')
              .contains('Monthly')
              .should('not.be.disabled')
              .click();
          });
        // on gitlab this api call always fails with code 1, yet the frequency is being changed
        // cy.waitForCall('updateEmailFrequency');
        // verify changes
        cy.getRowByText('projects-table', '[AWR Demo Project]')
          .within(() => {
            cy.getBySelector('email-updates-dropdown')
              .findBySelector('dropdown-button')
              .should('contain.text', 'Monthly')
              .and('not.be.disabled')
              .click();

            cy.getBySelector('dropdown-menu')
              .should('be.visible')
              .findBySelector('dropdown-item')
              .contains('Never')
              .should('not.be.disabled')
              .click();
          });
      });

      it('Test Delete Button', () => {
        cy.doNotSavePreferences('prefs');
        cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
        cy.visit('/');
        cy.waitForCall('getWebsitesPerformance');

        cy.getBySelector('management')
          .should('not.be.disabled')
          .click();

        cy.waitForCall('getWebsitesManagement');
        cy.wait('@prefs');

        cy.getRowByText('projects-table', '[AWR Demo Project]')
          .findBySelector('delete-button-actions')
          .should('not.be.disabled')
          .click();

        cy.getBySelector('simple-alert-modal')
          .should('be.visible')
          .findBySelector('ok-button')
          .should('not.be.disabled');
      });
    });
  });

  describe('Visual Tests', CypressImageDiffConfig, () => {
    it('Test visibility percent chart', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);

      cy.get('.filtersHead').hideElement();
      cy.getRowByText(table, 'advancedwebranking_VISUAL')
        .findBySelector('kpi-chart-container')
        .first()
        .compareSnapshot('kpi-chart');
    });

    it('Test difficulty section chart', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance', 'getSearchEngines']);

      cy.get('.filtersHead').hideElement();

      cy.getRowByText(table, 'advancedwebranking_VISUAL')
        .findBySelector('keyword-difficulty-chart')
        .should('be.visible')
        .scrollIntoView()
        .compareSnapshot('difficulty-section');
    });

    it('Test improved declined chart', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getSearchEngines']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance', 'getSearchEngines']);

      cy.get('.filtersHead').hideElement();
      cy.getRowByText(table, 'advancedwebranking_VISUAL')
        .findBySelector('improved-declined-chart')
        .should('be.visible')
        .scrollIntoView()
        .compareSnapshot('improved-declined-chart');

      cy.getRowByText(table, 'advancedwebranking_VISUAL')
        .findBySelector('improved-declined-ranked')
        .should('be.visible')
        .scrollIntoView()
        .compareSnapshot('improved-declined-ranked');
    });

    it('Test management tab- resources section chart', () => {
      cy.setupWaitForCalls(['getWebsitesPerformance', 'getWebsitesManagement']);
      cy.visit('/');
      cy.waitForCalls(['getWebsitesPerformance']);
      cy.doNotSavePreferences();
      cy.getBySelector('management').click();
      cy.waitForCall('getWebsitesManagement');
      cy.get('.filtersHead').hideElement();
      cy.getRowByText(table, 'advancedwebranking_VISUAL')
        .findBySelector('resources-consumption-chart')
        .scrollIntoView()
        .compareSnapshot('resources-consumption');
    });
  });
});

function checkDropdownKpis(selectors, table, tableRow, text) {
  cy.getRowByText(table, tableRow).within(() => {
    selectors.forEach((r) => {
      cy.selectDropdownOption(`${r}-kpi-container`, text);
      cy.assertDropdownSelectedOption(`${r}-kpi-container`, text);
    });
  });
}
