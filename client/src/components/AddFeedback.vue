<template>
  <div class="submit-form mt-3 mx-auto">
    <p class="headline">Add Feedback</p>

    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field
            v-model="feedback.title"
            :rules="[(v) => !!v || 'Title is required']"
            label="Title"
            required
        ></v-text-field>

        <v-text-field
            v-model="feedback.description"
            :rules="[(v) => !!v || 'Description is required']"
            label="Description"
            required
        ></v-text-field>

        <v-rating
            v-model="feedback.rating"
            hover
            length="5"
            size="40"
            value="3"
            background-color="black"
            color="black accent-4"
            label="Rating"
        ></v-rating>
      </v-form>

      <v-btn color="primary" class="mt-3" @click="saveFeedback">Submit</v-btn>
    </div>

    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>
          Submitted successfully!
        </v-card-title>

        <v-card-subtitle>
          Click the button to add new Feedback.
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="success" @click="newFeedback">Add</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import FeedbackDataService from "../services/FeedbackDataService";

export default {
  name: "add-feedback",
  data() {
    return {
      feedback: {
        id: null,
        title: "",
        description: "",
        rating: 3,
        published: false,
      },
      submitted: false,
    };
  },
  methods: {
    saveFeedback() {
      var data = {
        title: this.feedback.title,
        description: this.feedback.description,
        rating: this.feedback.rating
      };

      FeedbackDataService.create(data)
        .then((response) => {
          this.feedback.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    newFeedback() {
      this.submitted = false;
      this.feedback = {};
    },
  },
};
</script>

<style>
.submit-form {
  max-width: 300px;
}
</style>
