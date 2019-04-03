Ext.define('VocApp.model.StockOHLC', {
    extend: 'VocApp.model.Base',

    fields: [
        'company',
        'time',
        'open',
        'high',
        'low',
        'close'
    ]
});
