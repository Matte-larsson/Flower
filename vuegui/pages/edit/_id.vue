<template lang="jade">
<div>
  <h1>Edit Flower</h1>

  <label for="name">name</label>
  <input v-model="name" placeholder="enter name">
  <br>
  <br>
  <label for="price">price</label>
  <input v-model.number="price" placeholder="enter price">
  <br>
  <br>
  <label for="stock">stock</label>
  <input v-model.number="stock" placeholder="enter stock">
  <div><button :disabled="validInput" @click="updateFlower()">spara</button></div>
  </div>


</div>
</template>

<script>
export default { 
  data() {
    return {
      name: "koko",
      price: undefined,
      stock: undefined,
    };
  },
    created: function () {
      console.log("hio");
        this.fetchFlower();
    },

  methods: {
        updateFlower: function () {
          this.$axios
            .post("/flowers/" +  this.$route.params.id + "/update", {
              fid: this.$route.params.id,
              name: this.name,
              price: this.price,
              stock: this.stock,
            })
            .then ((response) => {
              this.$router.back()
            });
        },
    
      fetchFlower: function () {
        this.$axios
          .get("/flowers/" + this.$route.params.id)
          .then ((response) => {
            let f = response.data[0]
            this.name = f.name
            this.price = f.price
            this.stock = f.stock
          });
      },
  },
};
</script>

