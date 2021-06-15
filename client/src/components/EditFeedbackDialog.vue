<template>
  <div class="text-center">
    <v-dialog
      class="dialog-container"
      v-model="dialog"
      width="500"
    >
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          Edit Feedback
        </v-card-title>

        <v-form ref="form" lazy-validation class="edit-form">
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
        </v-form>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="success"
            text
            @click.native="updateFeedback"
          >
            update
          </v-btn>
          <v-btn
            color="error"
            text
            @click.native="deleteFeedback"
          >
            delete
          </v-btn>
           <v-btn
            color="primary"
            text
            @click.native="cancel"
          >
            cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import FeedbackDataService from "../services/FeedbackDataService";

export default {
  name: "feedback",
  data() {
    return {
      dialog: false,
      resolve: null,
      reject: null,
      currentFeedback: {},
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
            this.resolve(true)
            this.dialog = false
        })
        .catch((e) => {
            console.log(e);
        });
    },
    deleteFeedback() {
      FeedbackDataService.delete(this.currentFeedback.id)
        .then((response) => {
            console.log(response.data);
            this.resolve(true)
            this.dialog = false
        })
        .catch((e) => {
            console.log(e);
        });
    },
    open(feedbackId) {
        this.getFeedback(feedbackId)
        this.dialog = true
        return new Promise((resolve, reject) => {
          this.resolve = resolve
          this.reject = reject
        })
    },
    cancel() {
        this.resolve(false)
        this.dialog = false
    }
  },
};
</script>

<style>
.edit-form {
  margin: auto;
  padding: 5%;
}
</style>
