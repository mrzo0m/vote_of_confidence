Ext.define('VocApp.view.profitloss.ProfitLossModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.profitloss',

    requires: [
        'VocApp.model.MetaProfitloss',
        'VocApp.model.FullProfitloss'
    ],

    stores: {
        metaProfitLoss: {
            model: 'VocApp.model.MetaProfitloss',
            autoLoad: true,

            listeners: {
                load: 'onMetaDataLoad'
            },

            proxy: {
                type: 'ajax',
                url: 'resources/data/meta_data.json',

                reader: {
                    type: 'json'
                }
            }
        }
    }
});
