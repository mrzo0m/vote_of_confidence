/**
 * Created by 16678596 on 28.03.2019.
 */
Ext.define('VocApp.view.main.Toolbar', {
    extend: 'Ext.Toolbar',
    xtype: 'maintoolbar',

    requires: [
        'Ext.Button',
        'Ext.Img',
        'Ext.SegmentedButton'
    ],

    items: [
        {
            ui: 'header',
            iconCls: 'x-fa fa-bars',
            margin: '0 0 0 10',
            handler: 'onMenuBarClick'
        },
        '->',
        {
            // This component is moved to the floating nav container by the phone profile
            xtype: 'button',
            // userCls: 'main-logo',
            plugins: {
                responsive: true
            },
            responsiveConfig: {
                'width < 768': {
                    margin: '0'
                },
                'width >= 768': {
                    hidden: false,
                    margin: '0 0 0 10',
                }
            },
            html: 'Заказчик'
        },
        {
            // This component is moved to the floating nav container by the phone profile
            xtype: 'button',
            // userCls: 'main-logo',
            plugins: {
                responsive: true
            },
            responsiveConfig: {
                'width < 768': {
                    margin: '0'
                },
                'width >= 768': {
                    hidden: false,
                    margin: '0 0 0 10',
                }
            },
            html: 'Эксперт'
        },
        {
            // This component is moved to the floating nav container by the phone profile
            ui: 'header',
            // userCls: 'main-logo',
            plugins: {
                responsive: true
            },
            responsiveConfig: {
                'width < 768': {
                    margin: '0'
                },
                'width >= 768': {
                    margin: '0 0 0 10',
                }
            },
            href: '#candidate',
            handler: 'toolbarButtonClick',
            text: 'Соискатель'
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
                    html: 'Goff Smith',
                    margin: '0 12 0 4',
                }
            },
            userCls: 'main-user-name'
        }
    ]
});