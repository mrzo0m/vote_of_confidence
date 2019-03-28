/**
 * Created by 16678596 on 28.03.2019.
 */
Ext.define('VocApp.view.faq.Faq', {
    extend: 'Ext.Panel',

    requires: [
        'VocApp.view.faq.FaqModel',
		'VocApp.view.faq.FaqController'
    ],

    xtype: 'faq',

    viewModel: {
        type: 'faq'
    },

    controller: 'faq',

    items: [
        {
            xtype: 'maintoolbar',
            docked: 'top',
            // userCls: 'main-toolbar',
            shadow: true
        }

    ]
});