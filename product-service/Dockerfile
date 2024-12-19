FROM python:3.9.20

WORKDIR /app

COPY app.py .

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

ENV PORT=5000
ENV DATABASE_URL=postgresql://postgres:1234@localhost/productdb

ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0

EXPOSE 5000

CMD ["sh" , "-c" , "flask run --port ${PORT}" ]