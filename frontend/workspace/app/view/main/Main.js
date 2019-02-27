/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 */
Ext.define('VocApp.view.main.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.MessageBox',
        'VocApp.view.widgets.Widgets',
        'Ext.layout.Fit'
    ],

    controller: 'main',
    viewModel: 'main',

    fullscreen: true,

    itemId: 'home',
    items: [
        {
            title: 'Работодатель',
            layout: 'fit',
            // The following grid shares a store with the classic version's grid as well!
            items: [{
                xtype: 'widgets'
            }]
        }, {
            title: 'Соискатель',
            layout: 'fit',
            // The following grid shares a store with the classic version's grid as well!
            items: [{
                xtype: 'mainlist'
            }]
        }, {
            title: 'Эксперт',
            bind: {
                html: '{loremIpsum}'
            }
        }
    ]
});
