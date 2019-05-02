/**
 * Created by olegbursinov on 2019-03-27.
 */
Ext.define('VocApp.store.Claim', {
    extend: 'Ext.data.Store',

    alias: 'store.claim',

    model: 'VocApp.model.Claim',
    autoLoad: true,
    autoSync: true,
    proxy: {
        type: 'ajax',
        api: {
            read: '/history-microservice/claims'
        },
        reader: {
            type: 'json'
        },
        listeners: {
            exception: function (proxy, response, operation) {
                Ext.MessageBox.show({
                    title: 'REMOTE EXCEPTION',
                    msg: operation.getError(),
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }
});