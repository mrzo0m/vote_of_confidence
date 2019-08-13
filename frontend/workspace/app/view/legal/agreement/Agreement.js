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
    cls: 'agreement',

    scrollable: 'y',

    viewModel: {
        type: 'agreement'
    },

    controller: 'agreement',

    items: [
        {
            xtype: 'component',
            cls: 'legal',
            bind: {
                html: '<h2>Соглашение об оказании услуг по содействию в трудоустройстве</h2>'
            }
        },
        {
            xtype: 'component',
            cls: 'legal',
            bind: {
                html: '{legal}'
            }
        }
    ]
});