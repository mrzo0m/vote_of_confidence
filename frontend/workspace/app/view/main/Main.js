/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 */
Ext.define('VocApp.view.main.Main', {
    extend: 'Ext.navigation.View',

    requires: [
        'Ext.Button',
        'Ext.list.Tree',
        'Ext.MessageBox',
        'VocApp.view.widgets.Widgets',
        'Ext.layout.Fit',
        'Ext.navigation.View'
    ],

    xtype: 'home',

    // controller: 'main',
    //viewModel: 'main',
    //
    navigationBar: false,

    items: [
        {
            xtype: 'maintoolbar',
            docked: 'top',
            // userCls: 'main-toolbar',
            shadow: true
        },
        {
            xtype: 'container',
            docked: 'top',
            // userCls: 'main-nav-container',
            reference: 'navigation',
            layout: 'fit',
            items: [{
                xtype: 'treelist',
                reference: 'navigationTree',
                scrollable: true,
                ui: 'nav',
                store: {},
                bind: {
                    store: '{navigationtree}'
                },
                expanderFirst: false,
                expanderOnly: false,
                listeners: {
                    itemclick: 'onNavigationItemClick',
                    selectionchange: 'onNavigationTreeSelectionChange'
                }
            }]
        }
    ]
});
