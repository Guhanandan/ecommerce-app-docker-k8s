from flask import Flask, jsonify , request
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv('DATABASE_URL', 'postgresql://postgres:1234@localhost:5432/productdb')
db = SQLAlchemy(app)

class Product(db.Model):
    __tablename__ = 'products'
    productid = db.Column(db.Integer, primary_key=True , autoincrement=True)
    productname = db.Column(db.String(100), nullable=False)
    productprice = db.Column(db.Float, nullable=False)

# base url request
@app.route('/' , methods=['GET'])
def index_page():
    return "Hello this the message from the flask app"

# Get Product by its id
@app.route('/products/<int:id>', methods=['GET'])
def get_product(id):
    product = Product.query.get_or_404(id)
    return jsonify({
        'productid': product.productid,
        'productname': product.productname,
        'productprice': product.productprice
    })

# Get all Products 
@app.route('/products', methods=['GET'])
def get_products():
    products = Product.query.all()
    products = [{
        'productid': product.productid,
        'productname': product.productname,
        'productprice': product.productprice
    } for product in products]
    return jsonify(products)

@app.route('/products', methods=['POST'])
def post_product():
    # Get data from the POST request
    data = request.get_json()

    # Validate the incoming data
    if not data or not all(key in data for key in ['productname', 'productprice']):
        return jsonify({"error": "Bad Request, please provide productname and productprice fields"}), 400

    # Create a new product
    new_product = Product(productname=data['productname'], productprice=data['productprice'])

    try:
        # Add and commit the new product to the database
        db.session.add(new_product)
        db.session.commit()

        return jsonify({"message": "Product created successfully", "product": {
            "productid": new_product.productid,
            "productname": new_product.productname,
            "productprice": new_product.productprice
        }}), 201
    except Exception as e:
        # Handle any errors (e.g., database errors)
        db.session.rollback()
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    
    app.run(debug=True, port = os.getenv('PORT'))
