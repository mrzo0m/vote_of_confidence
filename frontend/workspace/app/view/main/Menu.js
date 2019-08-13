/**
 * Created by 16678596 on 15.07.2019.
 */
Ext.define('VocApp.view.main.Menu', {
    extend: 'VocApp.view.widgets.TopToolbar',
    xtype: 'mainmenu',
    requires: [],


    cls: 'main-menu',
    layout: 'hbox',
    weighted: true,

    items: [{
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
    },
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
            href: '#legal',
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
        },
        '->',
        {
            ui: 'header',
            iconCls: 'x-fa fa-bars',
            margin: '0 0 0 10',
            handler: 'onMenuBarClick'
        }
    ]
});