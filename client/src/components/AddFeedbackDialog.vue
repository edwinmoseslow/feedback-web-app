<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      width="500"
    >
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          Add Feedback
        </v-card-title>

        <v-form ref="form" lazy-validation class="add-form">
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

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click.native="addFeedback"
          >
            add
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
    data: () => ({
      dialog: false,
      resolve: null,
      reject: null,
      feedback: {
        id: null,
        title: "",
        description: "",
        rating: 3,
        published: false,
      },
    }),
    methods: {
      addFeedback() {
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
            this.resolve(true)
            this.dialog = false
            this.feedback = {}
          })
          .catch((e) => {
            console.log(e);
            this.resolve(false)
            this.dialog = false
          });
      },
      open() {
        this.dialog = true
        return new Promise((resolve, reject) => {
          this.resolve = resolve
          this.reject = reject
        })
      },
      cancel() {
        this.resolve(false)
        this.dialog = false
        this.feedback = {}
      }
    }
  }
</script>

<style>
.add-form {
  margin: auto;
  padding: 5%;
}
</style>