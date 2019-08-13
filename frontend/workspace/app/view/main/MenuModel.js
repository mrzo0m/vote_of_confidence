/**
 * Created by 16678596 on 15.07.2019.
 */
Ext.define('VocApp.view.main.MenuModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.menu',


    stores: {
        navigationtree: {
            type: 'navigationtree'
        }
    },

    data: {
        name: 'VocApp',
        showNavigation: false,
        loremIpsum: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
    }
});