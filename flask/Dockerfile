FROM python:3.9-alpine
WORKDIR /app
COPY main.py requirements.txt /app/
RUN pip install -r requirements.txt
EXPOSE 5000
CMD ["python3", "main.py"]