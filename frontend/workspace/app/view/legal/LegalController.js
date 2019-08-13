/**
 * Created by olegbursinov on 2019-02-24.
 */
Ext.define('VocApp.view.legal.LegalController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.legal',


    routes: {
        'legal': {
            action: 'onLegalRoute',
            name: 'emptyterminalinfo'
        },
        'legal/': {
            action: 'onLegalRoute',
            name: 'emptyinfo'
        }
    },


    onLegalRoute: function () {
        this.redirectTo("agreement", {replace: true});
    },

    onRegisration: function () {
        this.redirectTo("login", {replace: true});
    }
});