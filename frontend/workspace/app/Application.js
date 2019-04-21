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
        'Ext.plugin.Responsive',
        'Ext.History'
    ],


    quickTips: false,
    defaultToken : 'about',

    viewport: {
        controller: 'viewport',
        viewModel: 'viewport'
    },

    launch: function(profile) {
        // The viewport controller requires xtype defined by profiles, so let's perform extra
        // initialization when the application and its dependencies are fully accessible.
        // Ext.Viewport.getController().onLaunch();
        Ext.History.init();
        Ext.getBody().removeCls('launching');
        this.callParent([profile]);
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
