/**
 * Created by olegbursinov on 2019-03-27.
 */
Ext.define('VocApp.model.Claim', {
    extend: 'Ext.data.Model',

    fields: [

        {name: 'claimInfo', type: 'string'},
        {name: 'key', type: 'auto'},
        {name: 'companyName', type: 'string', mapping:'key.companyName'},
        {name: 'id', type: 'string', mapping:'key.id'}
    ]
});