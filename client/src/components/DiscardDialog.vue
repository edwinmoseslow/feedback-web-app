<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      width="500"
    >
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          Discard Changes
        </v-card-title>

        <v-card-text>
          Discard Changes?
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click.native="agree"
          >
            ok
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
  export default {
    data: () => ({
      dialog: false,
      resolve: null,
      reject: null
    }),
    methods: {
      open() {
        this.dialog = true
        return new Promise((resolve, reject) => {
          this.resolve = resolve
          this.reject = reject
        })
      },
      agree() {
        this.resolve(true)
        this.dialog = false
      },
      cancel() {
        this.resolve(false)
        this.dialog = false
      }
    }
  }
</script>