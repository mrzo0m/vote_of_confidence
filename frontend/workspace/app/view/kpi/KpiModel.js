Ext.define('VocApp.view.kpi.KpiModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.kpi',

    requires: [
        'VocApp.model.Kpi',
        'VocApp.store.Kpi'
    ],

    data: {
        // This property is placed in the ViewModel by routing
        // kpiCategory: null
    },

    stores: {
        kpiMain: {
            type: 'kpi',
            autoLoad: true,
            filters: {
                property: 'category',
                value: '{kpiCategory}'
            }
        }
    }
});
