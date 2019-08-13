/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('VocApp.Application', {
    extend: 'Ext.app.Application',

    name: 'VocApp',

    requires: [
        'VocApp.*',
        'Ext.History',
        'Ext.plugin.Responsive',
        'Ext.MessageBox'
    ],

    mainView: 'VocApp.view.main.Main',

    // defaultToken: 'main',

    controllers: [
        // 'Action'    // creates one global instance of the Action controller
    ],

    stores: [
        'NavigationTree'
    ],

    // profiles: [
    //     'Phone',
    //     'Tablet'
    // ],

    viewport: {
        controller: 'viewport',
        viewModel: 'viewport'
    },

    launch: function(profile) {
        // The viewport controller requires xtype defined by profiles, so let's perform extra
        // initialization when the application and its dependencies are fully accessible.
        Ext.History.init();
        Ext.Viewport.getController().onLaunch();
        Ext.getBody().removeCls('launching');
        // this.callParent([profile]);
        this.callParent();
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
