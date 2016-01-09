using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace VindiciaSelectIntegration
{
    class PropertyFile
    {
        private Dictionary<String, String> list;
        private String filename;

        public PropertyFile(String file)
        {
            Console.WriteLine("Loading: " + file);
            reload(file);
        }

        public static PropertyFile getBundle(String propFile)
        {
            DirectoryInfo dir = new DirectoryInfo(Environment.CurrentDirectory);
            Console.WriteLine("Executing in: " + dir);
            DirectoryInfo parentDir = new DirectoryInfo(dir.Parent.FullName);
            DirectoryInfo projDir = new DirectoryInfo(parentDir.Parent.FullName);
            Console.WriteLine("Project: " + projDir);
            return new PropertyFile(projDir + "\\" + propFile + ".properties");
        }

        public String get(String field, String defValue)
        {
            return (get(field) == null) ? (defValue) : (get(field));
        }
        public String get(String field)
        {
            return (list.ContainsKey(field)) ? (list[field]) : (null);
        }

        public void set(String field, Object value)
        {
            if (!list.ContainsKey(field))
                list.Add(field, value.ToString());
            else
                list[field] = value.ToString();
        }

        public void Save()
        {
            Save(this.filename);
        }

        public void Save(String filename)
        {
            this.filename = filename;

            if (!System.IO.File.Exists(filename))
                System.IO.File.Create(filename);

            System.IO.StreamWriter file = new System.IO.StreamWriter(filename);

            foreach (String prop in list.Keys.ToArray())
                if (!IsNullOrWhiteSpace(list[prop]))
                    file.WriteLine(prop + "=" + list[prop]);

            file.Close();
        }

        public void reload()
        {
            reload(this.filename);
        }

        public void reload(String filename)
        {
            this.filename = filename;
            list = new Dictionary<String, String>();

            if (System.IO.File.Exists(filename))
                loadFromFile(filename);
            else
                System.IO.File.Create(filename);
        }

        public Boolean IsNullOrWhiteSpace(String s)
        {
            return ( ( null == s ) || s.Trim().Equals("") );
        }

        private void loadFromFile(String file)
        {
            foreach (String line in System.IO.File.ReadAllLines(file))
            {
                if ((!String.IsNullOrEmpty(line)) &&
                    (!line.StartsWith(";")) &&
                    (!line.StartsWith("#")) &&
                    (!line.StartsWith("'")) &&
                    (line.Contains('=')))
                {
                    int index = line.IndexOf('=');
                    String key = line.Substring(0, index).Trim();
                    String value = line.Substring(index + 1).Trim();

                    if ((value.StartsWith("\"") && value.EndsWith("\"")) ||
                        (value.StartsWith("'") && value.EndsWith("'")))
                    {
                        value = value.Substring(1, value.Length - 2);
                    }

                    try
                    {
                        //ignore dublicates
                        list.Add(key, value);
                    }
                    catch { }
                }
            }
        }


    }
}

