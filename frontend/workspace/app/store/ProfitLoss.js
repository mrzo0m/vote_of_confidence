Ext.define('VocApp.store.ProfitLoss', {
    extend: 'Ext.data.Store',
    alias: 'store.profitloss',

    model: 'VocApp.model.FullProfitloss',

    proxy: {
        type: 'ajax',
        url: 'resources/data/full_data.json',
        reader: 'json'
    }
});
