<template>
  <v-app>
    <v-app-bar app dark>
      <v-btn to="/feedbacks" text>
        Feedback
      </v-btn>

      <!-- <v-btn to="/add" text>
        Add Feedback
      </v-btn> -->
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
};
</script>
