/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'VocApp.Application',

    name: 'VocApp',

    requires: [
        // This will automatically load all classes in the VocApp namespace
        // so that application classes do not need to require each other.
        'VocApp.*'
    ],

    // The name of the initial view to create.
    mainView: 'VocApp.view.main.Main'
});
