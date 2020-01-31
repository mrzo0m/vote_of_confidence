<template>
    <div id="home">
        <div v-if="!this.$parent.authenticated">
            <button
                    v-on:click="login()"
            >
                Login
            </button>
        </div>
    </div>
</template>

<script>

    const PATH_TO_PROTECTED_ROUTE = '/private'

    export default {
        name: 'home',
        methods: {
            login () {
                this.$auth.loginRedirect(PATH_TO_PROTECTED_ROUTE)
            },
        }
    }
</script>