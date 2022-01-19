<template lang="jade">
<div>
  <div>
  <ul id="array-rendering">
    <li v-for="f in flowers">
      {{ f.id }} - {{f.name}} -{{f.price}} - {{f.stock}} - x // <--- Länk för en delete
    </li>
  </ul>
  </div>
  <div><button @click="fetchFlower">Hämta</button></div>
  <div><button @click="createFlower('ros')">Skapa</button></div>
</div>
</template>

<script>
export default {
  data() {
    return {
      flowers: [],
    };
  },
  methods: {
    fetchFlower: function () {
      this.$axios
        .get("/flowers")
        .then((response) => (this.flowers = response.data.flowers));
    },
    createFlower: function (name) {
      this.$axios
        .post("/flowers", {
          id: "fid1",
          name: name,
          price: 150,
          stock: 13
        });
    },
  },
};
</script>