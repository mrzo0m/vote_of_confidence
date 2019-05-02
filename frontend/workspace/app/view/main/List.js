/**
 * This view is an example list of people.
 */
Ext.define('VocApp.view.main.List', {
    extend: 'Ext.grid.Grid',
    xtype: 'mainlist',

    requires: [
        'VocApp.store.Personnel'
    ],

    title: 'Personnel',

    store: {
        type: 'claim'
    },

    columns: [{ 
        text: 'companyName',
        dataIndex: 'companyName',
        width: 100,
        cell: {
            userCls: 'bold'
        }
    }, {
        text: 'claimInfo',
        dataIndex: 'claimInfo',
        width: 230 
    }],

    listeners: {
        select: 'onItemSelected'
    }
});
