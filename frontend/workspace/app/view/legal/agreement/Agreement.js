/**
 * Created by olegbursinov on 2019-02-24.
 */
Ext.define('VocApp.view.legal.agreement.Agreement', {
    extend: 'Ext.Container',

    requires: [
        'VocApp.view.legal.agreement.AgreementModel',
        'VocApp.view.legal.agreement.AgreementController'
    ],

    xtype: 'agreement',
    itemId: 'agreement',

    viewModel: {
        type: 'agreement'
    },

    controller: 'agreement',

    items: [
        {
            xtype: 'component',
            bind: {
                html: '{legal}'
            },
            margin: '10%'
        }
    ]
});