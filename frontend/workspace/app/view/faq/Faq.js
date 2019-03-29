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
            xtype: 'statustile',
            height: 170,
            userCls: 'big-33 small-50 dashboard-item',
            shadow: true,
            color: '#153b56',
            quantity: 611,
            description: 'Messages' ,
            iconCls: 'x-fa fa-envelope'
        }
    ]
});