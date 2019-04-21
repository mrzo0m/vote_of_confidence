/**
 * Created by olegbursinov on 2019-02-24.
 */
Ext.define('VocApp.view.legal.agreement.AgreementController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.agreement',

    routes : {
        'agreement' : 'onAgreement'
    },


    onAgreement: function () {
        this.redirectTo('agreement');
    }

});