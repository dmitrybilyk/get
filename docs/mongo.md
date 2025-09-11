db.createCollection("posts")

show collections

const doc = {
title: "What is the best way to learn JavaScript from the ground up?",
postId: NumberInt(3511),
comments: 10,
shared: true,
tags: [
"JavaScript",
"programming"
],
author: {
name: "Mike Forester",
nickname: "mikef"
}
};

db.posts.insertOne({
title: "What is the average salary of the junior frontend developer?",
postId: NumberInt(1151),
comments: 0,
shared: false,
author: {
name: "Mike Forester",
nickname: "mikef"
}
})

db.posts.insertMany([{
title: "My thoughts about 12-factor App Methodology",
postId: NumberInt(2618),
comments: 0,
shared: false,
tags: [],
author: {
name: "Emily Watson",
nickname: "emily23"
}
}
,
{
title: "Who can suggest best computer coding book for beginners?",
postId: NumberInt(8451),
comments: 2,
shared: false,
tags: [
"programming",
"coding"
],
author: {
name: "Emily Watson",
nickname: "emily23"
}
}])

db.getCollection("posts").find({}).skip(2).sort({shared: 1})

db.getCollection("posts").find({comments: {$gt: 0}})

db.getCollection("posts").find({comments: {$lt: 5} })

db.getCollection("posts").find({
$and:[
{comments: {$lt: 5}} ,
{comments:{$gt: 0}}
]
})

db.getCollection("posts").find({
$or:[
{shared: true} ,
{tags:"programming"}
// {tags:{$eq: "programming"}}
]
})

db.getCollection("posts").find({
tags: {$in: ["programming", "coding"]}
// {tags:{$eq: "programming"}}
})

db.posts.find({
tags: {$in: ["programming", "coding"]}
// {tags:{$eq: "programming"}}
})


db.posts.find(
{postId: 1151}
)

db.posts.find(
{tags: []}
)

db.posts.updateOne(
{postId: 1151},
{$set: {title: "What is the averabe salary of Senior FE developer"}}
)

db.posts.updateOne(
{postId: 1151},
{$unset: {tags: 1}}
)


db.posts.aggregate([
{$group: {_id: "$author.name"}}
])

db.posts.getIndexes()

db.posts.find()
db.posts.findOne({"_id": ObjectId("68bd4a61f4898f48f527888a")})