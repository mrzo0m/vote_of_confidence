/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 */
Ext.define('VocApp.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    listen: {
        controller: {
            '#': {
                unmatchedroute: 'setCurrentView'
            }
        }
    },

    routes: {
        ':node': 'setCurrentView'
    },

    onItemSelected: function (sender, record) {
        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },

    setCurrentView: function (hashTag) {
        hashTag = (hashTag || '').toLowerCase();
        var tabs = this.getView();

        var tab = tabs.setActiveTab(id);
        if (tab) {
            // if we changed active tabs...
            var route = this.getTabRoute(tab);
            if (route && route !== id) {
                this.redirectTo(hashTag);
            }
        }
    }
});
