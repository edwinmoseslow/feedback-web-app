<template>
  <div v-if="currentFeedback" class="edit-form py-3">
    <p class="headline">Edit Feedback</p>

    <v-form ref="form" lazy-validation>
      <v-text-field
        v-model="currentFeedback.title"
        :rules="[(v) => !!v || 'Title is required']"
        label="Title"
        required
      ></v-text-field>

      <v-text-field
        v-model="currentFeedback.description"
        :rules="[(v) => !!v || 'Description is required']"
        label="Description"
        required
      ></v-text-field>

      <v-rating
            v-model="currentFeedback.rating"
            hover
            length="5"
            size="40"
            background-color="black"
            color="black accent-4"
            label="Rating"
        ></v-rating>

      <v-divider class="my-5"></v-divider>

      <v-btn color="error" small class="mr-2" @click="deleteFeedback">
        Delete
      </v-btn>

      <v-btn color="success" small @click="updateFeedback">
        Update
      </v-btn>
    </v-form>

    <p class="mt-3">{{ message }}</p>
  </div>

  <div v-else>
    <p>Please click on a Feedback...</p>
  </div>
</template>

<script>
import FeedbackDataService from "../services/FeedbackDataService";

export default {
  name: "feedback",
  data() {
    return {
      currentFeedback: null,
      message: "",
    };
  },
  methods: {
    getFeedback(id) {
      FeedbackDataService.get(id)
        .then((response) => {
          this.currentFeedback = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    updateFeedback() {
      FeedbackDataService.update(this.currentFeedback.id, this.currentFeedback)
        .then((response) => {
          console.log(response.data);
          this.message = "Feedback was updated successfully!";
        })
        .catch((e) => {
          console.log(e);
        });
    },

    deleteFeedback() {
      FeedbackDataService.delete(this.currentFeedback.id)
        .then((response) => {
          console.log(response.data);
          this.$router.push({ name: "feedbacks" });
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.message = "";
    this.getFeedback(this.$route.params.id);
  },
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>
