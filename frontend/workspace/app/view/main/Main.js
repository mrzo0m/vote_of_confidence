/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting causes an instance of this class to be created and
 * added to the Viewport container.
 */
Ext.define('VocApp.view.main.Main', {
    extend: 'Ext.Container',
    xtype: 'app-main',

    fullscreen: true,
    scrollable: 'y',

    requires: [
        'Ext.MessageBox',
        'Ext.Toolbar',
        'VocApp.view.widgets.Widgets'
    ],

    controller: 'main',
    viewModel: 'main',

    itemId: 'home',

    items: [
        {
            xtype: 'toolbar',
            docked: 'top',
            items: [
                {
                    ui: 'header',
                    iconCls: 'x-fa fa-bars',
                    margin: '0 0 0 10',
                    handler: 'onMenuBarClick'
                },
                '->',
                {
                    ui: 'header',
                    iconCls: 'x-fa fa-search',
                    href: '#searchresults',
                    plugins: {
                        responsive: true
                    },
                    responsiveConfig: {
                        'width < 768': {
                            hidden: true
                        },
                        'width >= 768': {
                            hidden: false,
                            margin: '0 7 0 0'
                        }
                    },

                    handler: 'toolbarButtonClick'
                }, {
                    ui: 'header',
                    iconCls: 'x-fa fa-envelope',
                    href: '#email',
                    plugins: {
                        responsive: true
                    },
                    responsiveConfig: {
                        'width < 768': {
                            hidden: true
                        },
                        'width >= 768': {
                            hidden: false,
                            margin: '0 7 0 0'
                        }
                    },
                    handler: 'toolbarButtonClick'
                }, {
                    ui: 'header',
                    iconCls: 'x-fa fa-question',
                    href: '#faq',
                    plugins: {
                        responsive: true
                    },
                    responsiveConfig: {
                        'width < 768': {
                            hidden: true
                        },
                        'width >= 768': {
                            hidden: false,
                            margin: '0 7 0 0'
                        }
                    },
                    handler: 'toolbarButtonClick'
                }, {
                    ui: 'header',
                    iconCls: 'x-fa fa-th-large',
                    href: '#dashboard',
                    plugins: {
                        responsive: true
                    },
                    responsiveConfig: {
                        'width < 768': {
                            hidden: true
                        },
                        'width >= 768': {
                            hidden: false,
                            margin: '0 7 0 0'
                        }
                    },
                    handler: 'toolbarButtonClick'
                }, {
                    xtype: 'component',
                    plugins: {
                        responsive: true
                    },
                    responsiveConfig: {
                        'width < 768': {
                            hidden: true
                        },
                        'width >= 768': {
                            hidden: false,
                            html: 'Маркел Андрошин',
                            margin: '0 12 0 4',
                        }
                    },
                    userCls: 'main-user-name'
                }
            ]
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
