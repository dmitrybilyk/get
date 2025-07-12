use testdb;

db.people.deleteMany({}); // Clear existing people
db.addresses.deleteMany({}); // Clear existing addresses

// Insert 30 people documents (1 to 30)
for (let i = 1; i <= 30; i++) {
db.people.insertOne({
"_id": i === 1 ? ObjectId("687214feb74015914413bd46") : new ObjectId(),
"_class": "com.knowledge.get.model.Person",
"email": `user${i}@test.com`,
"name": `User${i}`,
"age": 30
});
}

// Insert 4 addresses documents (2 for user1, 2 for new users 21 and 22)
db.addresses.insertMany([
{
"_id": "a1",
"city": "CityA",
"personId": ObjectId("687214feb74015914413bd46"),
"street": "123 Main St",
"zipCode": "12345"
},
{
"_id": "a2",
"city": "CityB",
"personId": ObjectId("687214feb74015914413bd46"),
"street": "456 Elm St",
"zipCode": "67890"
},
{
"_id": "a3",
"city": "CityC",
"personId": db.people.findOne({ "email": "user21@test.com" })._id, // Link to user21
"street": "789 Pine St",
"zipCode": "98765"
},
{
"_id": "a4",
"city": "CityD",
"personId": db.people.findOne({ "email": "user22@test.com" })._id, // Link to user22
"street": "101 Maple St",
"zipCode": "54321"
}
]);



db.people.insertOne(
{
"_id": new ObjectId,
"email": "test@gmail.com",
"_class": "com.knowledge.get.model.Person",
"name": "Dmytro",
"age": 44
}
)

db.people.find({
"age": 44
})

db.people.findOne({
"age": 30
})

db.addresses.countDocuments({"city": "CityA"})

db.people.updateOne(
{"email": "test@gmail.com"},
{$set: {"age": 31}}
)

db.people.find({"name": "Dmytro"})

db.people.aggregate([
{ $match: { "_id": ObjectId("687214feb74015914413bd46") } },
{
$lookup: {
from: "addresses",
localField: "_id",
foreignField: "personId",
as: "addresses"
}
},
{ $unwind: "$addresses" }
])

db.people.createIndex({ "name": "text"})
db.people.getIndexes()