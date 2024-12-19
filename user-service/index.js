const express = require('express');
const mongoose = require('mongoose');
const axios = require('axios');
const bcrypt = require('bcrypt');
require('dotenv').config();

const app = express();
app.use(express.json());

mongoose.connect(process.env.MONGO_URI)
  .then(() => console.log('MongoDB connected'))
  .catch((err)=> console.log(err));

// User schema
const userSchema = new mongoose.Schema({
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true }
});
const User = mongoose.model('User', userSchema);

app.get('/' , (req , res) => {
  res.status(200).json({message : "This is the Home page. Hello World!"});
});

app.post('/users/signup', async (req, res) => {
  try {
    const { name, email, password } = req.body;
    console.log(name , email , password);
    // Check if user already exists
    const existingUser = await User.findOne({ email });
    console.log(existingUser);
    if (existingUser) {
      return res.status(400).json({ message: 'User already exists' });
    }

    // Hash the password before saving to database
    const hashedPassword = await bcrypt.hash(password, 10);
    console.log(hashedPassword);
    // Create a new user
    const newUser = new User({
      name,
      email,
      password: hashedPassword
    });

    // Save the user to the database
    await newUser.save();

    res.status(201).json({ message: 'User signed up successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Error signing up user', error });
  }
});

// User login route
app.post('/users/login', async (req, res) => {
  try {
    const { email, password } = req.body;

    // Find the user by email
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }

    // Compare the entered password with the hashed password in the database
    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }

    res.status(200).json({ message: 'User logged in successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Error logging in user', error });
  }
});

app.get('/users' , async (req , res) => {
  try{
    const users = await User.find({});
    res.status(200).json({data : users});
  }
  catch(err){
    res.status(400).json({err : "Error fetching the users"});
  }
});

// Fetch user and their orders from Order Service
app.get('/users/:userId/orders', async (req, res) => {
  try {
    // const user = await User.findById(req.params.userId);
    // if (!user) return res.status(404).send('User not found');

    console.log(req.params.userId);
    // Fetch orders from Order Service for a user with userId
    const response = await axios.get(`${process.env.ORDER_SERVICE_URL}/orders/user/${req.params.userId}`);
    console.log(response.data);
    res.status(200).json({data : response.data});
  } catch (error) {
    res.status(500).send(error.message);
  }
});

// get all the products form the product service
app.get('/user/products' , async (req , res) => {
  try{
    // console.log("products")
    const products = await axios.get(`${process.env.PRODUCT_SERVICE_URL}/products`);
    console.log(products.data);
    res.status(200).json({data : products.data});
  }
  catch(err){
    res.status(500).json({error : err});
  }
});

// This router will place the order in the order service
app.post('/user/place-order' , async (req , res) => {
  try{
    await axios.post(`${process.env.ORDER_SERVICE_URL}/orders` , req.body);
    res.status(201).json({message : "Order placed successfully"})
  }
  catch(err){
    res.status(500).json({error : err});
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`User Service running on port ${PORT}`));
