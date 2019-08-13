/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 */
Ext.define('VocApp.view.main.Main', {
    extend: 'Ext.Container',
    xtype: 'main',
    fullscreen: true,
    layout: 'vbox',

    items: [
        {
            xtype: 'mainmenu',
            reference: 'mainmenu',
            zIndex: 4,
            docked: 'top'
        },
        {
            xtype: 'container',
            userCls: 'main-nav-container',
            reference: 'navigation',
            scrollable: true,
            items: [
                {
                    xtype: 'treelist',
                    reference: 'navigationTree',
                    ui: 'navigation',
                    store: 'NavigationTree',
                    zIndex: 4,
                    height: '100%',
                    width: '100%',
                    padding: '10 0 0 0',
                    expanderOnly: false,
                    listeners: {
                        itemclick: 'onNavigationItemClick',
                        selectionchange: 'onNavigationTreeSelectionChange'
                    }
                }
            ]
        },
        {
            xtype: 'navigationview',
            flex: '1',
            reference: 'mainCard',
            layout: {
                type: 'card',
                animation: false
            },
            userCls: 'main-container',
            navigationBar: false,
            itemId: 'navigationView',
            items: [
                {
                    xtype: 'legal'
                }
            ]
        }
    ]
});
