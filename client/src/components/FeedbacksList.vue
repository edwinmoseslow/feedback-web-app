<template>
  <v-row align="center" class="list px-3 mx-auto">
    <v-col cols="12" md="8">
      <v-text-field v-model="title" label="Search by Title"></v-text-field>
    </v-col>

    <v-col cols="12" md="4">
      <v-btn small @click="searchTitle">
        Search
      </v-btn>
    </v-col>

    <v-col cols="12" md="4">
      <v-btn small @click="showAddFeedback()">
        Add Feeback
      </v-btn>
    </v-col>

    <v-col cols="12" sm="12">
      <v-card class="mx-auto" tile>
        <v-card-title>Feedbacks</v-card-title>

        <v-data-table
          :headers="headers"
          :items="feedbacks"
          disable-pagination
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="showEditFeedback(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deleteFeedback(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>

        <DiscardDialog ref="discard"/>
        <AddFeedbackDialog ref="addFeedback"/>
        <EditFeedbackDialog ref="editFeedback"/>

        <v-card-actions v-if="feedbacks.length > 0">
          <v-btn small color="error" @click="removeAllFeedbacks">
            Remove All
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import FeedbackDataService from "../services/FeedbackDataService";
import DiscardDialog from "../components/DiscardDialog.vue";
import AddFeedbackDialog from "../components/AddFeedbackDialog.vue";
import EditFeedbackDialog from "../components/EditFeedbackDialog.vue"
export default {
  name: "feedbacks-list",
  components: {
    DiscardDialog,
    AddFeedbackDialog,
    EditFeedbackDialog
  },
  data() {
    return {
      feedbacks: [],
      title: "",
      headers: [
        { text: "Title", align: "start", sortable: false, value: "title" },
        { text: "Description", value: "description", sortable: false },
        { text: "Rating", value: "rating", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
    };
  },
  methods: {
    async showAddFeedback() {
      if (await this.$refs.addFeedback.open()) {
        console.log("Feedback added")
        this.retrieveFeedbacks();
      }
    },
    async showEditFeedback(id) {
      if (await this.$refs.editFeedback.open(id)) {
        console.log("Feedback edited")
        this.retrieveFeedbacks();
      }
    },
    retrieveFeedbacks() {
      FeedbackDataService.getAll()
        .then((response) => {
          this.feedbacks = response.data.map(this.getDisplayFeedback) ?? [];
        })
        .catch(() => {
          this.feedbacks = [];
        });
    },
    removeAllFeedbacks() {
      FeedbackDataService.deleteAll()
        .then((response) => {
          console.log(response.data);
          this.retrieveFeedbacks();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchTitle() {
      FeedbackDataService.findByTitle(this.title)
        .then((response) => {
          this.feedbacks = response.data.map(this.getDisplayFeedback);
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteFeedback(id) {
      FeedbackDataService.delete(id)
        .then(() => {
          this.retrieveFeedbacks();
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getDisplayFeedback(feedback) {
      return {
        id: feedback.id,
        title: feedback.title.length > 30 ? feedback.title.substr(0, 30) + "..." : feedback.title,
        description: feedback.description.length > 30 ? feedback.description.substr(0, 30) + "..." : feedback.description,
        rating: feedback.rating
      };
    },
  },
  mounted() {
    this.retrieveFeedbacks();
  },
  // async beforeRouteLeave(to, from, next) {
  //   if (await this.$refs.discard.open()) {
  //     // yes
  //     next();
  //   }
  // }
};
</script>

<style>
.list {
  max-width: 750px;
}
</style>